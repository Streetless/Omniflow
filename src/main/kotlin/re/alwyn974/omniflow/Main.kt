package re.alwyn974.omniflow

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import io.github.cdimascio.dotenv.dotenv
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.codec.digest.DigestUtils
import re.alwyn974.omniflow.Extensions.Companion.toLinux
import re.alwyn974.omniflow.config.Config
import re.alwyn974.omniflow.model.*
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Predicate
import kotlin.io.path.isDirectory

val logger = KotlinLogging.logger("OmniFlow")

fun main(args: Array<String>) = mainBody {
    val dotenv = dotenv()

    ArgParser(args).parseInto(::Args).run {
        logger.info { "Directory: $directory" }
        logger.info { "Version: $version" }
        logger.info { "Project Type: $projectType" }
        logger.info { "Mode: $mode" }
        logger.info { "Force clear: $clear" }
        logger.info { "Build Type: $buildType" }

        val config = Config.fromEnv(dotenv)
        start(this, config)
    }
}

/**
 * Start the process
 * @param args the arguments
 * @param config the configuration
 */
fun start(args: Args, config: Config) {
    Minio().use { minio ->
        minio.init(config)
        minio.ensureBucket(config.tempBucketName)
        minio.ensureBucket(config.vrBucketName)
        minio.ensureBucket(config.editorBucketName)


        return when (args.mode) {
            Mode.NEW -> makeNewVersion(args, config, minio)
            Mode.TEMPORARY -> makeTemporaryVersion(args, config, minio)
        }
    }
}

/**
 * Create a new version. It will download the temporary version, create a new version manifest and upload it to the bucket
 * @param args the arguments
 * @param config the configuration
 * @param minio the minio client
 */
fun makeNewVersion(args: Args, config: Config, minio: Minio) {
    val tempPrefix = Path.of(args.projectType.name.lowercase(), args.buildType.name.lowercase(), "v${args.version}")
    val bucket = if (args.projectType == ProjectType.SIMULATOR) config.vrBucketName else config.editorBucketName
    val prefix = Path.of(args.buildType.name.lowercase(), "v${args.version}")
    logger.info { "Creating new version ${args.version} of type ${args.buildType}" }

    val currentManifest = getManifest(config.manifestUrl.format(args.projectType.name.lowercase()))
    val json = Json { prettyPrint = true }

    val rootTempDir = try {
        Files.createTempDirectory("omniflow")
    } catch (e: Exception) {
        logger.error { "Failed to create temporary directory" }
        Files.createDirectories(Path.of("omniflow"))
    }
    val tempDir = rootTempDir.resolve(args.buildType.name.lowercase()).resolve("v${args.version}")

    logger.info { "Downloading temporary version to $tempDir" }
    minio.downloadDir(config.tempBucketName, tempPrefix, tempDir)
    logger.info { "Downloaded temporary version to $tempDir" }

    val files = mutableListOf<FileModel>()
    logger.info { "Creating version files" }
    Files.walk(tempDir).filter(Predicate.not(Path::isDirectory)).forEach {
        val relativePath = tempDir.relativize(it)
        val bytes = DigestUtils.digest(DigestUtils.getSha1Digest(), it.toFile())
        val hash = DigestUtils.sha1Hex(bytes)
        val size = it.toFile().length()
        val remotePath = prefix.resolve(relativePath)
        logger.debug { "Adding ${it.fileName} to version ${args.version} with remote path $remotePath" }
        files.add(FileModel(relativePath.toLinux(), hash, size, remotePath.toLinux()))
    }

    val version = VersionModel(args.version, args.buildType.name.lowercase(), files)
    val versionJson = json.encodeToString(version)
    val versionManifestPath = tempDir.resolve("files.json")
    Files.writeString(versionManifestPath, versionJson)

    logger.info { "Creating version manifest" }
    val versionManifest = VersionManifest(
        args.version,
        args.buildType.name.lowercase(),
        DigestUtils.sha1Hex(versionJson),
        prefix.resolve("files.json").toLinux()
    )
    val manifest = if (currentManifest == null) {
        ManifestModel(
            LatestVersionModel(args.buildType, args.version),
            listOf(versionManifest)
        )
    } else {
        val newManifest = addVersionOrReplace(currentManifest, versionManifest)
        newManifest.latest.setVersionFromBuildType(args.version, args.buildType)
        newManifest
    }

    logger.info { "Creating manifest" }
    val manifestPath = rootTempDir.resolve("manifest.json")
    Files.writeString(manifestPath, json.encodeToString(manifest))

    files.forEach {
        val fromPath = Path.of(args.projectType.name.lowercase(), it.path)
        logger.debug { "Uploading ${it.name} to $bucket from ${fromPath.toLinux()} to ${it.path}" }
        minio.copyFileFromBucket(config.tempBucketName, bucket, fromPath, Path.of(it.path))
    }

    minio.uploadFile(bucket, manifestPath, Path.of("manifest.json"))
    minio.uploadFile(bucket, versionManifestPath, prefix.resolve("files.json"))
}

/**
 * Create a temporary version. It will upload the directory to the temporary bucket
 * @param args the arguments
 * @param config the configuration
 * @param minio the minio client
 */
fun makeTemporaryVersion(args: Args, config: Config, minio: Minio) {
    val prefix = Path.of(args.projectType.name.lowercase(), args.buildType.name.lowercase(), "v${args.version}")
    logger.info { "Creating temporary version ${args.version} of type ${args.buildType}" }
    if (args.clear) {
        logger.warn { "Clear mode enable, directory will be cleared before upload" }
        minio.clearDirectory(config.tempBucketName, prefix)
    }
    minio.uploadDir(config.tempBucketName, args.directory, prefix)
}

/**
 * Get the manifest from the given url
 * @param url the url
 * @return the manifest
 */
fun getManifest(url: String): ManifestModel? = runBlocking {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    try {
        val manifest: ManifestModel =  client.get(url).body()
        return@runBlocking manifest
    } catch (e: Exception) {
        e.printStackTrace()
        logger.error { "Failed to get manifest from $url" }
        return@runBlocking null
    }
}

/**
 * Add a version to the manifest or replace it if it already exists
 * @param manifest the manifest
 * @param version the version
 * @return the new manifest
 */
fun addVersionOrReplace(manifest: ManifestModel, version: VersionManifest): ManifestModel {
    val newVersions = manifest.versions.toMutableList()
    val index = newVersions.indexOfFirst { it.version == version.version && it.type == version.type }
    if (index != -1)
        newVersions[index] = version
    else
        newVersions.add(version)
    return manifest.copy(versions = newVersions)
}
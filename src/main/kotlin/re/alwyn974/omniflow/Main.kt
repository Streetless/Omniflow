package re.alwyn974.omniflow

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import io.github.cdimascio.dotenv.dotenv
import io.github.oshai.kotlinlogging.KotlinLogging
import re.alwyn974.omniflow.config.MinioConfig
import java.nio.file.FileVisitOption
import java.nio.file.Path
import java.util.stream.Collectors
import kotlin.math.log

val logger = KotlinLogging.logger("OmniFlow")

// create a new version, temp s3 > get all files > hash sha1 + size > make JSON > upload all files to release s3

fun main(args: Array<String>) = mainBody {
    val dotenv = dotenv()

    ArgParser(args).parseInto(::Args).run {
        logger.info { "Directory: $directory" }
        logger.info { "Version: $version" }
        logger.info { "Project Type: $projectType" }
        logger.info { "Mode: $mode" }
        logger.info { "Force clear: $clear" }
        logger.info { "Build Type: $buildType" }

        val minioConfig = MinioConfig.fromEnv(dotenv)
        start(this, minioConfig)
    }
}

fun start(args: Args, minioConfig: MinioConfig) {
    Minio().use { minio ->
        minio.init(minioConfig)
        minio.ensureBucket(minioConfig.tempBucketName)
        minio.ensureBucket(minioConfig.vrBucketName)
        minio.ensureBucket(minioConfig.editorBucketName)


        when (args.mode) {
            Mode.NEW -> makeNewVersion(args, minioConfig, minio)
            Mode.TEMPORARY -> makeTemporaryVersion(args, minioConfig, minio)
        }
    }
}

fun makeNewVersion(args: Args, minioConfig: MinioConfig, minio: Minio) {
    val prefix = Path.of(args.projectType.name.lowercase(), args.buildType.name.lowercase(), "v${args.version}")
}

fun makeTemporaryVersion(args: Args, minioConfig: MinioConfig, minio: Minio) {
    val prefix = Path.of(args.projectType.name.lowercase(), args.buildType.name.lowercase(), "v${args.version}")
    logger.info { "Creating temporary version ${args.version} of type ${args.buildType}" }
    if (args.clear) {
        logger.warn { "Clear mode enable, directory will be cleared before upload" }
        minio.clearDirectory(minioConfig.tempBucketName, prefix)
    }
    minio.uploadDir(minioConfig.tempBucketName, args.directory, prefix)
}
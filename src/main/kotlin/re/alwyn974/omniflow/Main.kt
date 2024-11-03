package re.alwyn974.omniflow

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import io.github.cdimascio.dotenv.dotenv
import io.github.oshai.kotlinlogging.KotlinLogging
import io.minio.ListObjectsArgs
import re.alwyn974.omniflow.config.MinioConfig
import java.util.*

val logger = KotlinLogging.logger("OmniFlow")

// create a new version, temp s3 > get all files > hash sha1 + size > make JSON > upload all files to release s3

fun main(args: Array<String>) = mainBody {
    val dotenv = dotenv()

    ArgParser(args).parseInto(::Args).run {
        logger.info { "Directory: $directory" }
        logger.info { "Version: $version" }
        logger.info { "Project Type: $projectType" }
        logger.info { "Mode: $mode" }

        val minioConfig = MinioConfig.fromEnv(dotenv)
        start(this, minioConfig)
    }
}

fun start(args: Args, minioConfig: MinioConfig) {
    val minio = Minio()
    minio.init(minioConfig)

    minio.ensureBucket(minioConfig.tempBucketName)
    minio.ensureBucket(minioConfig.vrBucketName)
    minio.ensureBucket(minioConfig.editorBucketName)

    logger.info { "Uploading ${args.directory} to ${minioConfig.tempBucketName}" }
    val prefix = "%s/v%s/".format(args.projectType.name.lowercase(Locale.getDefault()), args.version)

//    minio.minioClient.listObjects(ListObjectsArgs.builder().bucket(minioConfig.tempBucketName).build()).forEach {
//        val item = it.get();
//        logger.info { "${item.etag()} ${item.size()} ${item.objectName()} ${item.storageClass()} ${item.owner()} ${item.userMetadata()} ${item.userTags()} ${item.isLatest()} ${item.versionId()} ${item.isDir()} ${item.isDeleteMarker()} ${item.userMetadata()} ${item.userTags()} ${item.isLatest()} ${item.versionId()} ${item.isDir()} ${item.isDeleteMarker()}" }
//    }

//    minio.uploadDir(minioConfig.tempBucketName, args.directory)
}

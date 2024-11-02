package re.alwyn974.omniflow

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import io.github.cdimascio.dotenv.dotenv
import io.github.oshai.kotlinlogging.KotlinLogging
import re.alwyn974.omniflow.config.MinioConfig

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
        val minio = Minio()
        minio.init(minioConfig)
    }
}

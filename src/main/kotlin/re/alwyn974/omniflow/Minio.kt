package re.alwyn974.omniflow

import io.github.oshai.kotlinlogging.KotlinLogging
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.UploadObjectArgs
import re.alwyn974.omniflow.config.MinioConfig
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries

class Minio {
    private val logger = KotlinLogging.logger("Minio")
    lateinit var minioClient: MinioClient
        private set;

    fun init(minioConfig: MinioConfig) {
        minioClient = MinioClient.builder()
            .endpoint(minioConfig.endpoint, minioConfig.port, minioConfig.useSSL)
            .credentials(minioConfig.accessKey, minioConfig.secretKey)
            .build()
    }

    fun ensureBucket(bucketName: String) {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            logger.info { "$bucketName didn't exist, creating it" }
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build())
        }
    }

    fun uploadFile(bucketName: String, filePath: Path, prefix: String = "") {
        val remotePath = prefix + filePath.fileName.toFile()
        logger.debug { "Uploading $filePath to $bucketName with remote path $remotePath" }
        minioClient.uploadObject(UploadObjectArgs.builder()
            .bucket(bucketName)
            .filename(filePath.toString())
            .`object`(remotePath)
            .build())
    }

    fun uploadDir(bucketName: String, dir: Path, prefix: String = "") {
        if (!dir.isDirectory()) {
            logger.error { "$dir is not a directory" }
            return
        }
        logger.debug { "Uploading directory $dir to $bucketName" }
        dir.listDirectoryEntries().forEach {
            if (it.isDirectory())
                uploadDir(bucketName, it, prefix)
            else
                uploadFile(bucketName, dir.resolve(it.fileName), prefix)
        }
    }

}
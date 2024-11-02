package re.alwyn974.omniflow

import io.github.oshai.kotlinlogging.KotlinLogging
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import re.alwyn974.omniflow.config.MinioConfig

class Minio {
    private val logger = KotlinLogging.logger("Minio")
    lateinit var minioClient: MinioClient
        private set

    fun init(minioConfig: MinioConfig) {
        minioClient = MinioClient.builder()
            .endpoint(minioConfig.endpoint)
            .credentials(minioConfig.accessKey, minioConfig.secretKey)
            .build()
    }

    fun ensureBucket(bucketName: String) {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            logger.info { "$bucketName didn't exist, creating it" }
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build())
        }
    }

}
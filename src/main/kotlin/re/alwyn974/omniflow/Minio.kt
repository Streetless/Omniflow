package re.alwyn974.omniflow

import io.github.oshai.kotlinlogging.KotlinLogging
import io.minio.*
import io.minio.messages.Item
import re.alwyn974.omniflow.config.MinioConfig
import java.nio.file.FileVisitOption
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import kotlin.io.path.isDirectory

class Minio : AutoCloseable {
    private val logger = KotlinLogging.logger("Minio")
    private lateinit var minioClient: MinioClient

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

    @Throws(Exception::class)
    fun uploadFile(bucketName: String, filePath: Path, objectName: Path, prefix: Path): ObjectWriteResponse {
        val remotePath = prefix.resolve(objectName).toLinux()
        logger.debug { "Uploading $filePath to $bucketName with remote path $remotePath" }
        return minioClient.uploadObject(
            UploadObjectArgs.builder()
                .bucket(bucketName)
                .filename(filePath.toString())
                .`object`(remotePath)
                .build()
        )
    }

    @Throws(IllegalArgumentException::class)
    fun uploadDir(bucketName: String, dir: Path, prefix: Path = Path.of("")): List<ObjectWriteResponse> {
        if (!dir.isDirectory())
            throw IllegalArgumentException("$dir is not a directory")

        logger.debug { "Uploading directory $dir to $bucketName" }
        return Files.walk(dir, FileVisitOption.FOLLOW_LINKS).use { paths ->
            paths.filter { !it.isDirectory() }
                .map { uploadFile(bucketName, it, dir.relativize(it), prefix) }
                .collect(Collectors.toList())
        }
    }

    fun listDirectory(
        bucketName: String,
        recursive: Boolean = true,
        includeUserMetadata: Boolean = true
    ): Iterable<Result<Item>> {
        return minioClient.listObjects(
            ListObjectsArgs.builder()
                .includeUserMetadata(includeUserMetadata)
                .recursive(recursive)
                .bucket(bucketName)
                .build()
        )
    }

    fun clearDirectory(bucketName: String, directory: Path) {
        val files = listDirectory(bucketName).filter { it.get().objectName().startsWith(directory.toLinux()) }
        files.forEach {
            logger.debug { "Removing ${it.get().objectName()}" }
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .`object`(it.get().objectName())
                    .build()
            )
        }
    }

    private fun Path.toLinux(): String {
        if (!System.getProperty("os.name").lowercase().contains("win"))
            return this.toString()
        return this.toString().replace("\\", "/")
    }

    override fun close() {
        logger.info { "Closing Minio client" }
        this.minioClient.close()
    }
}
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
import kotlin.math.log

class Minio : AutoCloseable {
    private val logger = KotlinLogging.logger("Minio")
    private lateinit var minioClient: MinioClient

    /**
     * Initialize the Minio client.
     * @param minioConfig The Minio configuration
     */
    fun init(minioConfig: MinioConfig) {
        minioClient = MinioClient.builder()
            .endpoint(minioConfig.endpoint, minioConfig.port, minioConfig.useSSL)
            .credentials(minioConfig.accessKey, minioConfig.secretKey)
            .build()
    }

    /**
     * Ensure a bucket exists. It will create it if it doesn't.
     * @param bucketName The name of the bucket
     */
    fun ensureBucket(bucketName: String) {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            logger.info { "$bucketName didn't exist, creating it" }
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build())
        }
    }

    /**
     * Upload a file to a bucket.
     * @param bucketName The name of the bucket
     * @param filePath The path of the file to upload
     * @param objectName The name of the object in the bucket
     * @param prefix The prefix to add to the remote path
     * @return The ObjectWriteResponse
     */
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

    /**
     * Upload a directory to a bucket.
     * @param bucketName The name of the bucket
     * @param dir The directory to upload
     * @param prefix The prefix to add to the remote path
     * @return A list of ObjectWriteResponse
     * @throws IllegalArgumentException If the path is not a directory
     */
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

    /**
     * Download a file from a bucket.
     * @param bucketName The name of the bucket
     * @param objectName The name of the object in the bucket
     * @param filePath The path to save the file
     */
    fun downloadFile(bucketName: String, objectName: String, filePath: Path) {
        logger.debug { "Downloading $objectName from $bucketName to $filePath" }
        minioClient.downloadObject(
            DownloadObjectArgs.builder()
                .bucket(bucketName)
                .`object`(objectName)
                .filename(filePath.toString())
                .build()
        )
    }

    /**
     * Download a directory from a bucket.
     * @param bucketName The name of the bucket
     * @param fromDir The directory to download
     * @param toDir The directory to save the files
     */
    fun downloadDir(bucketName: String, fromDir: Path, toDir: Path) {
        logger.debug { "Downloading directory $fromDir from $bucketName to $toDir" }
        val files = listDirectory(bucketName, fromDir)
        files.forEach {
            val filePath = toDir.resolve(fromDir.relativize(Path.of(it.get().objectName())))
            if (!Files.exists(filePath.parent)) {
                logger.debug { "Creating directory ${filePath.parent}" }
                Files.createDirectories(filePath.parent)
            }
            downloadFile(bucketName, it.get().objectName(), filePath)
        }
    }

    /**
     * List all files in a bucket.
     * @param bucketName The name of the bucket
     * @param recursive If the listing should be recursive
     * @param includeUserMetadata If the listing should include user metadata
     * @return An iterable of Result<Item>
     */
    fun listDirectory(
        bucketName: String,
        directory: Path = Path.of(""),
        recursive: Boolean = true,
        includeUserMetadata: Boolean = true
    ): Iterable<Result<Item>> {
        return minioClient.listObjects(
            ListObjectsArgs.builder()
                .includeUserMetadata(includeUserMetadata)
                .recursive(recursive)
                .prefix(directory.toLinux())
                .bucket(bucketName)
                .build()
        )
    }

    /**
     * Clear a directory in a bucket.
     * @param bucketName The name of the bucket
     * @param directory The directory to clear
     */
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

    /**
     * Copy a file from a path to another in different buckets.
     * @param fromBucket The source bucket
     * @param toBucket The destination bucket
     * @param fromPath The source path
     * @param toPath The destination path
     */
    fun copyFileFromBucket(fromBucket: String, toBucket: String, fromPath: Path, toPath: Path) {
        logger.debug { "Copying $fromPath from $fromBucket to $toPath in $toBucket" }
        minioClient.copyObject(
            CopyObjectArgs.builder()
                .source(CopySource.builder().bucket(fromBucket).`object`(fromPath.toLinux()).build())
                .bucket(toBucket)
                .`object`(toPath.toLinux())
                .build()
        )
    }

    /**
     * Copy a file from a path to another in the same bucket.
     * @param bucketName The bucket name
     * @param from The source path
     * @param to The destination path
     */
    fun copyFile(bucketName: String, from: Path, to: Path) {
        logger.debug { "Copying $from to $to in $bucketName" }
        minioClient.copyObject(
            CopyObjectArgs.builder()
                .source(CopySource.builder().bucket(bucketName).`object`(from.toLinux()).build())
                .bucket(bucketName)
                .`object`(to.toLinux())
                .build()
        )
    }


    /**
     * Move a file from a path to another in the same bucket.
     * @param bucketName The name of the bucket
     * @param from The path of the file to move
     * @param to The path of the file to move to
     */
    fun moveFile(bucketName: String, from: Path, to: Path) {
        logger.debug { "Moving $from to $to in $bucketName" }
        minioClient.copyObject(
            CopyObjectArgs.builder()
                .source(CopySource.builder().bucket(bucketName).`object`(from.toLinux()).build())
                .bucket(bucketName)
                .`object`(to.toLinux())
                .build()
        )
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(bucketName)
                .`object`(from.toLinux())
                .build()
        )
    }

    /**
     * Convert a path to a Linux path.
     * Windows is trash.
     */
    private fun Path.toLinux(): String {
        if (!System.getProperty("os.name").lowercase().contains("win"))
            return this.toString()
        return this.toString().replace("\\", "/")
    }

    /**
     * Close the Minio client.
     * @see AutoCloseable
     */
    override fun close() {
        logger.info { "Closing Minio client" }
        this.minioClient.close()
    }
}
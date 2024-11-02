package re.alwyn974.omniflow.config

import io.github.cdimascio.dotenv.Dotenv

data class MinioConfig(val accessKey: String, val secretKey: String, val endpoint: String, val port: Int, val useSSL: Boolean, val tempBucketName: String, val editorBucketName: String, val vrBucketName: String) {
    companion object {
        fun fromEnv(dotenv: Dotenv): MinioConfig {
            return MinioConfig(
                dotenv.get("MINIO_ACCESS_KEY") ?: "minioadmin",
                dotenv.get("MINIO_SECRET_KEY") ?: "minioadmin",
                dotenv.get("MINIO_ENDPOINT") ?: "localhost",
                dotenv.get("MINIO_PORT")?.toInt() ?: 9000,
                dotenv.get("MINIO_USE_SSL")?.toBoolean() ?: true,
                dotenv.get("MINIO_TEMP_BUCKET_NAME") ?: "temporary-release",
                dotenv.get("MINIO_EDITOR_BUCKET_NAME") ?: "editor",
                dotenv.get("MINIO_VR_BUCKET_NAME") ?: "simulator"
            )
        }
    }
}

package re.alwyn974.omniflow.config

import io.github.cdimascio.dotenv.Dotenv

data class Config(
    val accessKey: String,
    val secretKey: String,
    val endpoint: String,
    val port: Int,
    val useSSL: Boolean,
    val tempBucketName: String,
    val editorBucketName: String,
    val vrBucketName: String,
    val manifestUrl: String
) {
    companion object {
        fun validateEnv(dotenv: Dotenv) {
            val requiredKeys = listOf(
                "MINIO_ACCESS_KEY",
                "MINIO_SECRET_KEY",
                "MINIO_ENDPOINT",
                "MINIO_PORT",
                "MINIO_USE_SSL",
                "MINIO_TEMP_BUCKET_NAME",
                "MINIO_EDITOR_BUCKET_NAME",
                "MINIO_VR_BUCKET_NAME",
                "MANIFEST_URL"
            )

            val missingKeys = requiredKeys.filter { dotenv[it] == null }
            if (missingKeys.isNotEmpty()) {
                throw IllegalArgumentException("Missing environment variables: $missingKeys")
            }
        }

        fun fromEnv(dotenv: Dotenv): Config {
            return Config(
                dotenv.get("MINIO_ACCESS_KEY") ?: "minioadmin",
                dotenv.get("MINIO_SECRET_KEY") ?: "minioadmin",
                dotenv.get("MINIO_ENDPOINT") ?: "localhost",
                dotenv.get("MINIO_PORT")?.toInt() ?: 9000,
                dotenv.get("MINIO_USE_SSL")?.toBoolean() ?: true,
                dotenv.get("MINIO_TEMP_BUCKET_NAME") ?: "temporary-release",
                dotenv.get("MINIO_EDITOR_BUCKET_NAME") ?: "editor",
                dotenv.get("MINIO_VR_BUCKET_NAME") ?: "simulator",
                dotenv.get("MANIFEST_URL") ?: "https://s3.envronment.com/%s/manifest.json"
            )
        }
    }
}

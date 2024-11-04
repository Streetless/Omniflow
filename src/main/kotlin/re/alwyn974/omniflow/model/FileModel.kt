package re.alwyn974.omniflow.model

import kotlinx.serialization.Serializable

@Serializable
data class FileModel(val name: String, val hash: String, val size: Long, val path: String)

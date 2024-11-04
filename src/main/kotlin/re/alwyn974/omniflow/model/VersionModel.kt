package re.alwyn974.omniflow.model

import kotlinx.serialization.Serializable

@Serializable
data class VersionModel(val version: String, val type: String, val files: List<FileModel>)

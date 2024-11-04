package re.alwyn974.omniflow.model

import kotlinx.serialization.Serializable

@Serializable
data class VersionManifest(val version: String, val type: String, val hash: String, val path: String)

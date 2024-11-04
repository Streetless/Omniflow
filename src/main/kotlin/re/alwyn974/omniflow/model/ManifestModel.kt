package re.alwyn974.omniflow.model

import kotlinx.serialization.Serializable

@Serializable
data class ManifestModel(val latest: LatestVersionModel, val versions: List<VersionManifest>)

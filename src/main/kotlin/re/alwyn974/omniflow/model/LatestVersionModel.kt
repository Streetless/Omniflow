package re.alwyn974.omniflow.model

import kotlinx.serialization.Serializable
import re.alwyn974.omniflow.BuildType

@Serializable
data class LatestVersionModel(var release: String?, var debug: String?) {
    constructor(buildType: BuildType, version: String) : this(
        if (buildType == BuildType.RELEASE) version else null,
        if (buildType == BuildType.DEBUG) version else null
    )

    fun setVersionFromBuildType(version: String, buildType: BuildType) {
        when (buildType) {
            BuildType.RELEASE -> release = version
            BuildType.DEBUG -> debug = version
        }
    }
}

package re.alwyn974.omniflow

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.SystemExitException
import com.xenomachina.argparser.default
import java.nio.file.Path
import java.nio.file.Paths

enum class Mode {
    TEMPORARY, // Upload to temporary bucket
    NEW, // Use temporary bucket content, to create a new version
}

enum class ProjectType {
    EDITOR,
    SIMULATOR,
}

enum class BuildType {
    RELEASE,
    DEBUG,
}

class Args(parser: ArgParser) {
    val directory: Path by parser.storing(
        "-d", "--directory",
        help = "The directory to push"
    ) { Paths.get(this).normalize() }
        .default(Path.of(""))
        .addValidator {
            if (mode == Mode.NEW)
                return@addValidator
            if (!value.toFile().exists())
                throw SystemExitException("Directory does not exist", 1)
        }

    val version by parser.storing(
        "-v", "--version",
        help = "The version to push"
    ).addValidator { if (value.isBlank()) throw SystemExitException("Version cannot be empty", 1) }

    val projectType by parser.mapping(
        "--editor" to ProjectType.EDITOR,
        "--simulator" to ProjectType.SIMULATOR,
        help = "The project type"
    )

    val mode by parser.mapping(
        "--new" to Mode.NEW,
        "--temporary" to Mode.TEMPORARY,
        help = "The mode to use"
    ).default(Mode.TEMPORARY)

    val clear by parser.flagging(
        "--clear", "-c",
        help = "Clear directory version before uploading"
    ).default(false)

    val buildType by parser.mapping(
        "--release" to BuildType.RELEASE,
        "--debug" to BuildType.DEBUG,
        help = "The build type"
    ).default(BuildType.RELEASE)
}
package re.alwyn974.omniflow

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.SystemExitException
import com.xenomachina.argparser.default
import java.nio.file.Path
import java.nio.file.Paths

enum class Mode {
    RELEASE,
    UPDATE,
}

enum class ProjectType {
    EDITOR,
    SIMULATOR,
}

class Args(parser: ArgParser) {
    val directory: Path by parser.storing(
        "-d", "--directory",
        help = "The directory to push"
    ) { Paths.get(this) }
        .addValidator { if (!value.toFile().exists()) throw SystemExitException("Directory does not exist", 1) }

    val version by parser.storing(
        "-v", "--version",
        help = "The version to push"
    )

    val projectType by parser.mapping(
        "--editor" to ProjectType.EDITOR,
        "--simulator" to ProjectType.SIMULATOR,
        help = "The project type"
    )

    val mode by parser.mapping(
        "--release" to Mode.RELEASE,
        "--update" to Mode.UPDATE,
        help = "The mode to use"
    ).default(Mode.RELEASE)
}
package re.alwyn974.omniflow

import java.nio.file.Path

class Extensions {
    companion object {
        /**
         * Convert a path to a Linux path.
         * Windows is trash.
         */
        fun Path.toLinux(): String {
            if (!System.getProperty("os.name").lowercase().contains("win"))
                return this.toString()
            return this.toString().replace("\\", "/")
        }
    }
}
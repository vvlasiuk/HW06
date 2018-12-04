package net.ukr.vlsv.hw06.Clases

class InternetInfo {
    fun getInternetStatus(): Boolean {
            var internetStatus: Boolean = false

            val runtime = Runtime.getRuntime()
            try {
                val ipProcess: Process  = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                val exitValue: Int = ipProcess.waitFor();

                internetStatus = exitValue == 0

            } finally {}

            return internetStatus
    }
}
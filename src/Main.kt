import java.io.File
import kotlin.system.exitProcess

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        exitWithError("Missing argument <path-to-library-root>")
    }

    val musicLibraryRoot = File(args[0])
    println("Music library root: '${musicLibraryRoot.absolutePath}'")

    if (!musicLibraryRoot.exists()) {
        exitWithError("Given path '${musicLibraryRoot.path}' does not exist!")
    }
}

fun exitWithError(message: String) {
    System.err.println(message)
    exitProcess(1)
}
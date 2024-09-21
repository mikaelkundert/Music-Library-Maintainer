import java.io.File
import java.util.LinkedList
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        exitWithError("Missing argument <path-to-library-root>")
    }

    val musicLibraryRoot = File(args[0])
    println("Music library root: '${musicLibraryRoot.absolutePath}'")

    listAllFilesInDirectoryTree(musicLibraryRoot)
    println("Completed")
}

fun exitWithError(message: String) {
    System.err.println(message)
    exitProcess(1)
}

fun listAllFilesInDirectoryTree(filepath: File) {
    if (!filepath.exists()) {
        exitWithError("Tried to list files from non-existing filepath '${filepath.absolutePath}'")
    }

    val stack = LinkedList<File>()
    stack.push(filepath)

    while (stack.isNotEmpty()) {
        val currentFilepath = stack.pop()
        val files = currentFilepath.listFiles()

        if (files != null) {
            for (file in files) {
                if (file.isDirectory) {
                    stack.push(file)
                } else {
                    println("Found file: '${file.absolutePath}'")
                }
            }
        }
    }
}
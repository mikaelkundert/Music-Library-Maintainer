import java.io.File
import java.util.LinkedList
import kotlin.system.exitProcess

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        exitWithError("Missing argument <path-to-library-root>")
    }

    val musicLibraryRoot = File(args[0])
    println("Music library root: '${musicLibraryRoot.absolutePath}'")

    listAllFilesInDirectoryTree(musicLibraryRoot)
    println("Completed")
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

fun exitWithError(message: String) {
    System.err.println(message)
    exitProcess(1)
}
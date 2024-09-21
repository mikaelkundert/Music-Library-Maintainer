package fi.kundert

import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import java.io.File
import java.util.LinkedList
import java.util.logging.Level
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
                    auditFile(file)
                }
            }
        }
    }
}

fun auditFile(file: File) {
    val supportedExtension = "flac"
    if (file.extension != supportedExtension) return

    println("Found file: '${file.absolutePath}'")
    AudioFileIO.logger.level = Level.OFF
    val audioFile = AudioFileIO.read(file)
    val fields = arrayOf(
        FieldKey.ALBUM_ARTIST,
        FieldKey.ARTIST,
        FieldKey.TITLE,
        FieldKey.ALBUM,
        FieldKey.DISC_NO,
        FieldKey.DISC_TOTAL,
        FieldKey.GENRE,
        FieldKey.TRACK,
        FieldKey.TRACK_TOTAL,
        FieldKey.YEAR,
        FieldKey.COMMENT,
    )
    for (fieldKey in fields) {
        val tagValue = audioFile.tag.getFirst(fieldKey)
        if (tagValue.isEmpty()) continue
        auditLog("${fieldKey.name}: $tagValue")
    }
}

fun auditLog(message: String) {
    println("  > $message")
}

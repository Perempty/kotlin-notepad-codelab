package chriscorptechnologies.learning.kotlin.notepad.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import chriscorptechnologies.learning.kotlin.notepad.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

/**
 * Created by Christophe KINDJI on 23/01/2018.
 */

private val TAG = "storage"

fun persistNote(context: Context, note: Note) {

    var fileName = note.fileName

    if (TextUtils.isEmpty(fileName)) {
        fileName = UUID.randomUUID().toString() + ".note"
    }

    Log.i(TAG, "Saving note $note")
    val fileOutput = context.openFileOutput(fileName, Context.MODE_PRIVATE)
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(note)
    outputStream.close()
}

fun loadNotes(context: Context): MutableList<Note> {
    val listNotes = mutableListOf<Note>()
    val noteDir = context.filesDir

    for (filename in noteDir.list()) {
        val note = loadNote(context, filename)
        Log.i(TAG, "Loaded note: $note")
        listNotes.add(note)
    }

    return listNotes
}

fun deleteNote(context: Context, note: Note) {
    context.deleteFile(note.fileName)
}

private fun loadNote(context: Context, fileName: String): Note {
    val fileInput = context.openFileInput(fileName)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note

    return note
}
package chriscorptechnologies.learning.kotlin.notepad

import android.app.Activity
import android.app.FragmentManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() {

    companion object {
        val REQUEST_EDIT_NOTE = 1
        val REQUEST_TEST_NOTE = 2
        val EXTRA_NOTE_KEY = "note"
        val EXTRA_NOTE_INDEX_KEY = "noteIndex"

        val ACTION_SAVE_NOTE = "chriscorptechnologies.learning.kotlin.notepad.actions.ACTION_SAVE_NOTE"
        val ACTION_DELETE_NOTE = "chriscorptechnologies.learning.kotlin.notepad.actions.ACTION_DELETE_NOTE"
    }

    lateinit var note: Note
    var noteIndex = -1

    lateinit var noteTitleView: TextView
    lateinit var noteTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar = toolbar as Toolbar

        //noteTitleView = findViewById<TextView>(R.id.detail_note_title_et)
        //noteTextView = findViewById<TextView>(R.id.detail_note_text_et)

        noteTitleView = detail_note_title_et
        noteTextView = detail_note_text_et

        note = intent.getParcelableExtra<Note>(EXTRA_NOTE_KEY)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX_KEY, -1)

        noteTitleView.text = note.title
        noteTextView.text = note.text

        toolbar.title = "Edition de ${note.title}"
        setSupportActionBar(toolbar)

        //Géré le bouton back (retour) à l'activité parent -> la suite défini dans le fichier manifest
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_note_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save -> {
                saveNote()
                return true
            }
            R.id.action_delete -> {
                showConfirmDeleteNoteDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showConfirmDeleteNoteDialog() {
        val confirmFragment = ConfirmDeleteNoteDialogFragment.newInstance(note.title)
        confirmFragment.listener = object : ConfirmDeleteNoteDialogFragment.ConfirmDeleteDialogListener {
            override fun onDialogPositiveClick() {
                deleteNote()
            }

            override fun onDialogNegativeClick() {}
        }
        confirmFragment.show(supportFragmentManager, "ConfirmDeleteDialog")
    }

    private fun saveNote() {
        note.title = noteTitleView.text.toString()
        note.text = noteTextView.text.toString()

        intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(EXTRA_NOTE_KEY, note as Parcelable)
        intent.putExtra(EXTRA_NOTE_INDEX_KEY, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()

    }

    private fun deleteNote() {
        intent = Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX_KEY, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


}

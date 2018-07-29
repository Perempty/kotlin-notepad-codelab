package chriscorptechnologies.learning.kotlin.notepad

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import chriscorptechnologies.learning.kotlin.notepad.utils.deleteNote
import chriscorptechnologies.learning.kotlin.notepad.utils.loadNotes
import chriscorptechnologies.learning.kotlin.notepad.utils.persistNote
import kotlinx.android.synthetic.main.activity_note_list.*

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = NoteListActivity::class.java.simpleName

    //Déclaration de listNote de type MutableList car nous voulons modifier la liste
    //Utilisation de lateinit pour initialiser la variable plus tard

    lateinit var listNotes: MutableList<Note>
    lateinit var noteAdapter: NoteAdapter
    lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)


        //Récupération de ma toolbar et afectation à l'activité
        val toolbar = toolbar as Toolbar
        setSupportActionBar(toolbar)

        //Click sur le bouton FAB
        create_note_fab.setOnClickListener(this)

        listNotes = loadNotes(this)

        noteAdapter = NoteAdapter(listNotes, this)

        /*val recyclerView = findViewById<RecyclerView>(R.id.note_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter*/

        note_recycler_view.layoutManager = LinearLayoutManager(this)
        note_recycler_view.adapter = noteAdapter

        coordinatorLayout = coordinator_layout

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != Activity.RESULT_OK || data == null) {
            return
        }

        when(requestCode) {
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }
    }

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX_KEY, -1)
        when(data.action) {
            NoteDetailActivity.ACTION_SAVE_NOTE -> {
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE_KEY)
                saveNote(note, noteIndex)
            }
            NoteDetailActivity.ACTION_DELETE_NOTE -> {
                deleteNote(noteIndex)
            }
        }


    }

    private fun saveNote(note: Note, noteIndex: Int) {
        persistNote(this, note)
        if(noteIndex < 0) {
            listNotes.add(0, note)
        } else {
            listNotes[noteIndex] = note
        }

        noteAdapter.notifyDataSetChanged()
    }

    private fun deleteNote(noteIndex: Int) {
        if(noteIndex < 0) {
            return
        }
        val note = listNotes.removeAt(noteIndex)
        deleteNote(this, note)
        noteAdapter.notifyDataSetChanged()
        Snackbar.make(coordinator_layout, "${note.title} supprimée", Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(view: View) {

        if(view.tag != null) {
            val index = view.tag as Int
            showNoteDetail(index)
        } else {
            when(view.id) {
                R.id.create_note_fab -> {
                    createNewNote()
                }
            }
        }
    }

    private fun createNewNote() {
        showNoteDetail(-1)
    }

    fun showNoteDetail(noteIndex: Int) {
        /** On crée une nouvelle @Note  si l'index est -1 donc appelé par {@ sinon on recupére la note de la listNotes **/
        val note = if(noteIndex < 0) Note() else listNotes[noteIndex]

        //Démarrage de l'activité Détail
        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_KEY, note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX_KEY, noteIndex)
        //startActivity(intent)
        startActivityForResult(intent, NoteDetailActivity.REQUEST_EDIT_NOTE)

    }
}

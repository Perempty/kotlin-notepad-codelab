package chriscorptechnologies.learning.kotlin.notepad

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment

/**
 * Created by Christophe KINDJI on 20/01/2018.
 */
class ConfirmDeleteNoteDialogFragment() : DialogFragment() {

    companion object {
        val EXTRA_NOTE_TITLE = "noteTitle"

        fun newInstance(noteTitle: String): ConfirmDeleteNoteDialogFragment {
            val fragment = ConfirmDeleteNoteDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_NOTE_TITLE, noteTitle)
            }
            return fragment
        }
    }

    //Création d'interface pour récupérer le résultat de l'action de l'utilisateur
    interface ConfirmDeleteDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    var listener: ConfirmDeleteDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Récupératiion du titre passé en parametre
        val noteTitle = arguments!!.getString(EXTRA_NOTE_TITLE)

        //Création de la boite de dialogue
        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Etes vous sûr de supprimer la note \"$noteTitle\"")
                .setPositiveButton("Supprimer", DialogInterface.OnClickListener { dialog, id ->
                    listener?.onDialogPositiveClick()
                })
                .setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, id ->
                    listener?.onDialogNegativeClick()
                })

        return builder.create()
    }
}
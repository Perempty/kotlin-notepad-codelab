package chriscorptechnologies.learning.kotlin.notepad

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_note.view.*

/**
 * Created by Christophe KINDJI on 18/01/2018.
 */
class NoteAdapter(val listNotes: List<Note>, val itemClickListener: View.OnClickListener)
    : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Récupération des différents View de notre item

        //Utilisant l'extension Kotlin
        val cardView = itemView.card_view
        val noteTitleView = cardView.note_title_tv
        val noteExcerptView = cardView.note_excerpt_tv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Recupération du layout et conversion en objet Kotlin
        val viewItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Récupération de la donnée à afficher qui est une variable de type @Note
        val note = listNotes[position]

        //Recupération l'évenement clique sur un item
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position

        //Mise à jour des champs selon la position du ViewHolder
        holder.noteTitleView.text = note.title
        holder.noteExcerptView.text = note.text
    }


    override fun getItemCount(): Int {
        return listNotes.size
    }
}
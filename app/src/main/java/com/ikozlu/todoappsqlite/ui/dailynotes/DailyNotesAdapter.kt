package com.ikozlu.todoappsqlite.ui.dailynotes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ikozlu.todoappsqlite.data.model.Note
import com.ikozlu.todoappsqlite.databinding.ItemDailyNoteBinding

class DailyNotesAdapter(
    private val dailyNotesListener: DailyNotesListener
) :RecyclerView.Adapter<DailyNotesAdapter.DailyNoteViewHolder>() {

    private val noteList = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyNoteViewHolder {
        val binding = ItemDailyNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyNoteViewHolder(binding, dailyNotesListener)
    }

    override fun onBindViewHolder(holder: DailyNoteViewHolder, position: Int){
        holder.bind(noteList[position])
    }

    inner class DailyNoteViewHolder(
        private val binding: ItemDailyNoteBinding,
        private val dailyNotesListener: DailyNotesListener
    ):RecyclerView.ViewHolder(binding.root){


        fun bind(note: Note){
            with(binding){
                tvTitle.text = note.title
                tvDesc.text = note.description
                tvPriority.text = note.priority



                root.setOnClickListener {
                    dailyNotesListener.onNoteClick(note.description)
                }
                
                checkBox.setOnClickListener {
                    dailyNotesListener.onDeleteClick(note.id)
                }



                when (note.priority) {
                    "High" -> cardView.setCardBackgroundColor(Color.RED)
                    "Medium" -> cardView.setCardBackgroundColor(Color.BLUE)
                    "Low" -> cardView.setCardBackgroundColor(Color.GREEN)
                    else -> cardView.setCardBackgroundColor(Color.WHITE)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun updateList(list: List<Note>, isAdd: Boolean? =null){
        noteList.clear()
        noteList.addAll(list)

        when (isAdd) {
            true -> notifyItemRangeChanged(0,list.size)
            false -> notifyItemRangeRemoved(0,list.size)
            else -> notifyItemRangeChanged(0,list.size)
        }

    }

    interface DailyNotesListener {
        fun onNoteClick(description: String)
        fun onDeleteClick(id: Int)
    }


}
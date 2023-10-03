package com.ikozlu.todoappsqlite.ui.dailynotes

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.ikozlu.todoappsqlite.R
import com.ikozlu.todoappsqlite.common.viewBinding
import com.ikozlu.todoappsqlite.data.model.Note
import com.ikozlu.todoappsqlite.data.source.Database
import com.ikozlu.todoappsqlite.databinding.DialogAddNoteBinding
import com.ikozlu.todoappsqlite.databinding.FragmentDailyNotesBinding


class DailyNotesFragment : Fragment(R.layout.fragment_daily_notes) , DailyNotesAdapter.DailyNotesListener {

    private val binding by viewBinding(FragmentDailyNotesBinding::bind)

    private lateinit var db: Database

    private val dailyNotesAdapter = DailyNotesAdapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Database(requireContext())


        with(binding){
            rvDailyNotes.adapter = dailyNotesAdapter
            dailyNotesAdapter.updateList(db.getDailyNotes())

            fabAdd.setOnClickListener {
                showAddDialog()
            }
        }

    }

    private fun showAddDialog(){
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = DialogAddNoteBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()


        with(dialogBinding){

            btnAddNote.setOnClickListener {
                val title = etTitle.text.toString()
                val desc = etDesc.text.toString()

                val priority = when {
                    rbLow.isChecked -> "Low"
                    rbMedium.isChecked -> "Medium"
                    rbHigh.isChecked -> "High"
                    else -> ""
                }

                if (title.isNotEmpty() && desc.isNotEmpty()){
                    db.addDailyNotes(Note(title,desc,priority,id))
                    dailyNotesAdapter.updateList(db.getDailyNotes(), true)
                    dialog.dismiss()
                }else{
                    Toast.makeText(requireContext(), "Please fill in the blanks !", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog.show()

    }

    override fun onNoteClick(description: String) {
        Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(id: Int) {
        db.deleteDailyNotes(id)
        dailyNotesAdapter.updateList(db.getDailyNotes(), false)

    }


}

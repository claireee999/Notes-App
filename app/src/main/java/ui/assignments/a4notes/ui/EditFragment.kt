package ui.assignments.a4notes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ui.assignments.a4notes.R
import ui.assignments.a4notes.viewmodel.NotesViewModel

class EditFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        val model: NotesViewModel by activityViewModels { NotesViewModel.Factory }
        val id = model.getEditID()
        val note = model.getNoteById(id)

        val title = model.getNoteTitle(note)
        val content = model.getNoteContent(note)
        val archived = model.getNoteArchived(note)
        val important = model.getNoteImportant(note)

        view.findViewById<EditText>(R.id.editNoteTitle).setText(title!!)
        view.findViewById<EditText>(R.id.editNoteContent).setText(content!!)
        view.findViewById<Switch>(R.id.editNoteArchived).isChecked = archived
        view.findViewById<Switch>(R.id.editNoteImportant).isChecked = important


        view.findViewById<EditText>(R.id.editNoteTitle).addTextChangedListener() {
            model.editNoteTitle(id, view.findViewById<EditText>(R.id.editNoteTitle).text.toString())
        }
        view.findViewById<EditText>(R.id.editNoteContent).addTextChangedListener() {
            model.editNoteContent(id, view.findViewById<EditText>(R.id.editNoteContent).text.toString())
        }
        view.findViewById<Switch>(R.id.editNoteArchived).setOnCheckedChangeListener { _, isChecked ->
            model.editNoteArchived(id, isChecked)
            view.findViewById<Switch>(R.id.editNoteImportant).isChecked = model.getNoteImportant(note)
        }
        view.findViewById<Switch>(R.id.editNoteImportant).setOnCheckedChangeListener { _, isChecked ->
            model.editNoteImportant(id, isChecked)
            view.findViewById<Switch>(R.id.editNoteArchived).isChecked = model.getNoteArchived(note)
        }


        return view
    }
}


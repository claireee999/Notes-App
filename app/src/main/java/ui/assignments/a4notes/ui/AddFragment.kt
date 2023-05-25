package ui.assignments.a4notes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ui.assignments.a4notes.R
import ui.assignments.a4notes.viewmodel.NotesViewModel

class AddFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        val model: NotesViewModel by activityViewModels { NotesViewModel.Factory }
        view.findViewById<Button>(R.id.createBtn).setOnClickListener {
            val title = view.findViewById<EditText>(R.id.addNoteTitle).text.toString()
            val content = view.findViewById<EditText>(R.id.addNoteContent).text.toString()
            if (title != "" || content != "") {
                model.addNote(title, content, view.findViewById<Switch>(R.id.addNoteImportant).isChecked)
                findNavController().navigate(R.id.addToMain)
            }
        }
        return view
    }
}
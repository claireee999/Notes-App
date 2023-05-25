package ui.assignments.a4notes.ui

import android.view.View
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import ui.assignments.a4notes.R
import ui.assignments.a4notes.viewmodel.NotesViewModel

class DisplayFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_display, container, false)
        val model: NotesViewModel by activityViewModels { NotesViewModel.Factory }
        view.findViewById<Switch>(R.id.showArchived).setOnCheckedChangeListener { _, isChecked ->
            model.setViewArchived(isChecked)
        }
        view.findViewById<Button>(R.id.addNoteBtn).setOnClickListener {
            findNavController().navigate(R.id.mainToAdd)
        }
        model.getArchivedObserve().observe(viewLifecycleOwner) {
            val notes = model.getNotes().value
            updateDisplay(notes!!, model, view)
        }

        model.getNotes().observe(viewLifecycleOwner) { notes -> updateDisplay(notes, model, view) }
        return view
    }

    private fun updateDisplay(notes: MutableList<LiveData<NotesViewModel.VMNote>>, model: NotesViewModel, view: View ) {
        val showArchived = model.getViewArchived()
        view.findViewById<Switch>(R.id.showArchived).isChecked = showArchived!!
        val importantNotes = view.findViewById<LinearLayout>(R.id.notes_important)
        val regularNotes = view.findViewById<LinearLayout>(R.id.notes_regular)
        val archivedNotes = view.findViewById<LinearLayout>(R.id.notes_archived)
        importantNotes.removeAllViews()
        regularNotes.removeAllViews()
        archivedNotes.removeAllViews()
        notes.forEach { note ->
            val title = model.getNoteTitle(note)
            val content = model.getNoteContent(note)
            val archived = model.getNoteArchived(note)
            val important = model.getNoteImportant(note)
            val id = model.getNoteId(note)
            layoutInflater.inflate(R.layout.note_view, null, false).apply {
                findViewById<TextView>(R.id.noteview_title).text = title!!
                findViewById<TextView>(R.id.noteview_content).text = content!!
                findViewById<LinearLayout>(R.id.noteView).setOnClickListener{
                    model.setEditID(id)
                    findNavController().navigate(R.id.mainToEdit)
                }
                findViewById<Button>(R.id.archiveBtn).setOnClickListener{
                    val archived = model.getNoteArchived(note)
                    model.editNoteArchived(id,!archived)
                }
                findViewById<Button>(R.id.deleteBtn).setOnClickListener{
                    model.deleteNote(id)
                }

                if (important) importantNotes.addView(this)
                else if (archived && showArchived == true) archivedNotes.addView(this)
                else if (!important && !archived ) regularNotes.addView(this)
            }
        }
    }
}
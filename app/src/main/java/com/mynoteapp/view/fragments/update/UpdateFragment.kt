package com.mynoteapp.view.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mynoteapp.R
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.databinding.FragmentUpdateBinding
import com.mynoteapp.view.ShareViewModel
import com.mynoteapp.view.fragments.list.NoteViewModel

class UpdateFragment : Fragment() {

    // View Binding
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    // Safe Args
    private val args by navArgs<UpdateFragmentArgs>()

    // View Model
    private val mShareViewModel: ShareViewModel by viewModels()
    private val mNoteViewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragments
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args

        // Activate Option Menu
        setHasOptionsMenu(true)

        binding.sUpdateNote.onItemSelectedListener = mShareViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_update, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_update_save -> updateNote()
            R.id.menu_update_delete -> deleteNote()
        }
//        if (item.itemId == R.id.menu_update_save) {
//            updateNote()
//        } else if (item.itemId == R.id.menu_update_delete) {
//            deleteNote()
//        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateNote() {
        val title = binding.etUpdateNoteTitle.text.toString()
        val descNote = binding.etUpdateNoteDesc.text.toString()
        val getPriority = binding.sUpdateNote.selectedItem.toString()

        val validation = mShareViewModel.verifyDataFromUser(title, descNote)
        if (validation) {
            // Update Note Data
            val updateNoteData = NoteData(
                args.noteParcel.id,
                title,
                mShareViewModel.parsePriority(getPriority),
                descNote
            )
            mNoteViewModel.updateData(updateNoteData)
            Toast.makeText(requireContext(), "Successfully Updated.", Toast.LENGTH_LONG).show()
            // Navigate Back To List Fragment
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please Fill Out All Fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    // Show Alert Dialog to Confirm Removal
    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete '${args.noteParcel.title}?'")
        builder.setMessage("Are you sure you want to remove '${args.noteParcel.title}?'")
        builder.setPositiveButton("Yes") { _, _ ->
            mNoteViewModel.deleteData(args.noteParcel)
            Toast.makeText(
                requireContext(),
                "Successfully Removed: ${args.noteParcel.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
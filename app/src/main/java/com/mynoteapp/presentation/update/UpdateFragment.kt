package com.mynoteapp.presentation.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mynoteapp.R
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.databinding.FragmentUpdateBinding
import com.mynoteapp.presentation.ShareViewModel
import com.mynoteapp.presentation.list.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment(R.layout.fragment_update), MenuProvider {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val mShareViewModel: ShareViewModel by viewModels()
    private val mNoteViewModel: NoteViewModel by viewModels()

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpdateBinding.bind(view)

//        binding.args = args
//        binding.sUpdateNote.onItemSelectedListener = mShareViewModel.listener


    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_update, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_update_save -> updateNote()
            R.id.menu_update_delete -> deleteNote()
        }
//        if (item.itemId == R.id.menu_update_save) {
//            updateNote()
//        } else if (item.itemId == R.id.menu_update_delete) {
//            deleteNote()
//        }
        return false
    }

    private fun updateNote() {
        val title = binding.etUpdateNoteTitle.text.toString()
        val descNote = binding.etUpdateNoteDesc.text.toString()
        val getPriority = binding.sUpdateNote.selectedItem.toString()

        val validation = mShareViewModel.verifyDataFromUser(title, descNote)
        if (validation) {
            val updateNoteData = NoteData(
                args.noteParcel.id,
                title,
                descNote,
                mShareViewModel.parsePriority(getPriority)
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
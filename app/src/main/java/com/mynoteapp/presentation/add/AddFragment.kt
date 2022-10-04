package com.mynoteapp.presentation.add

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
import com.mynoteapp.R
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.databinding.FragmentAddBinding
import com.mynoteapp.presentation.ShareViewModel
import com.mynoteapp.presentation.list.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add), MenuProvider {

    // View Binding
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    // View Model
    private val mNoteViewModel: NoteViewModel by viewModels()
    private val mShareViewModel: ShareViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddBinding.bind(view)

        binding.sAddNote.onItemSelectedListener = mShareViewModel.listener
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_add, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.check_add_item) {
            insertDataToDatabase()
        }
        return true
    }

    private fun insertDataToDatabase() {
        val mTitle = binding.etAddNoteTitle.text.toString()
        val mPriority = binding.sAddNote.selectedItem.toString() // spinner always has a value
        val mDesc = binding.etAddNoteMultiLine.text.toString()
        val validation = mShareViewModel.verifyDataFromUser(mTitle, mDesc)
        if (validation) {
            // insert data to database
            val newData = NoteData(
                0,
                mTitle,
                mShareViewModel.parsePriority(mPriority),
                mDesc
            )
            mNoteViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added.", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }

    }

}
package com.mynoteapp.view.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mynoteapp.R
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.databinding.FragmentAddBinding
import com.mynoteapp.view.ShareViewModel
import com.mynoteapp.view.fragments.list.NoteViewModel

class AddFragment : Fragment() {

    // View Binding
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    // View Model
    private val mNoteViewModel: NoteViewModel by viewModels()
    private val mShareViewModel: ShareViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragments
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        /** CODE HERE*/

        // ACTIVATE OPTION MENU
        setHasOptionsMenu(true)
        binding.sAddNote.onItemSelectedListener = mShareViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.check_add_item) {
            insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
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
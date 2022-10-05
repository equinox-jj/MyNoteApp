package com.mynoteapp.presentation.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mynoteapp.R
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.databinding.FragmentListBinding
import com.mynoteapp.presentation.ShareViewModel
import com.mynoteapp.presentation.list.adapter.ListAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), MenuProvider {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val mListAdapter: ListAdapter by lazy { ListAdapter() }

    private val mNoteViewModel: NoteViewModel by viewModels()
    private val mShareViewModel: ShareViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)

        binding.lifecycleOwner = this
        binding.mSharedViewModel = mShareViewModel

        setupRecycler()
        setupViewModel()
        searchItem()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_delete_all -> confirmDelete()
            R.id.p_high_item -> {
//                mListAdapter.setData(it)
            }
            R.id.p_low_item -> {
//                mListAdapter.setData(it)
            }
        }
        return false
    }

    private fun confirmDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete all data?")
        builder.setMessage("Are you sure you want to remove all data?")
        builder.setPositiveButton("Yes") { _, _ ->
            mNoteViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully Removed All Data",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()
    }

    private fun setupRecycler() {
        val recyclerView = binding.rvListFragment
        recyclerView.adapter = mListAdapter
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        recyclerView.setHasFixedSize(true)
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = mListAdapter.noteData[viewHolder.adapterPosition]

                mNoteViewModel.deleteData(deletedItem)
                mListAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                restoreDeleteItem(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteItem(view: View, deletedItem: NoteData) {
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            mNoteViewModel.insertData(deletedItem)
        }
        snackBar.show()
    }

    private fun setupViewModel() {
//        mNoteViewModel.getAllData.observe(viewLifecycleOwner) { data ->
//            mShareViewModel.checkIfDatabaseEmpty(data)
//            mListAdapter.setData(data)
//        }
    }

    private fun searchItem() {
        binding.svList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchThroughDatabase(query)
                    binding.svList.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchThroughDatabase(newText)
                    binding.svList.clearFocus()
                }
                return true
            }

        })

    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
//        mNoteViewModel.searchDatabase(searchQuery).observeOnce(this) { list ->
//            list?.let {
//                mListAdapter.setData(it)
//            }
//        }
    }

}
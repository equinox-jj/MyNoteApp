package com.mynoteapp.view.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mynoteapp.R
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.databinding.FragmentListBinding
import com.mynoteapp.util.observeOnce
import com.mynoteapp.view.ShareViewModel
import com.mynoteapp.view.fragments.list.adapter.ListAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment() {

    // View Model
    private val mNoteViewModel: NoteViewModel by viewModels()
    private val mShareViewModel: ShareViewModel by viewModels()

    // Adapter
    private val mListAdapter: ListAdapter by lazy { ListAdapter() }

    // View Binding
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mShareViewModel
        setHasOptionsMenu(true)

        setupRecycler()
        setupViewModel()
        searchItem()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmDelete()
            R.id.p_high_item -> mNoteViewModel.sortByHighPriority.observe(this) {
                mListAdapter.setData(
                    it
                )
            }
            R.id.p_low_item -> mNoteViewModel.sortByLowPriority.observe(this) {
                mListAdapter.setData(
                    it
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Show alert dialog to remove all note data.
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
        // Recycler Animation
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        recyclerView.setHasFixedSize(true)
        // Swipe Delete Item
        swipeToDelete(recyclerView)
    }

    // Swipe To Delete Item
    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = mListAdapter.noteData[viewHolder.adapterPosition]
                // Delete Item
                mNoteViewModel.deleteData(deletedItem)
                mListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                // Restore Deleted Item
                restoreDeleteItem(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    // Restore Delete Item
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
        mNoteViewModel.getAllData.observe(viewLifecycleOwner) { data ->
            mShareViewModel.checkIfDatabaseEmpty(data)
            mListAdapter.setData(data)
        }
    }

    // SEARCH LISTENER
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

        mNoteViewModel.searchDatabase(searchQuery).observeOnce(this) { list ->
            list?.let {
                mListAdapter.setData(it)
            }
        }
    }

}
package com.mynoteapp.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mynoteapp.common.NoteDiffUtil
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.databinding.ItemNoteLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var noteData = listOf<NoteData>()

    class MyViewHolder(private val binding: ItemNoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteData: NoteData) {
            binding.noteData = noteData
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(noteData[position])
    }

    override fun getItemCount(): Int = noteData.size

    fun setData(newData: List<NoteData>) {
        val noteDiffUtil = NoteDiffUtil(noteData, newData)
        val diffUtilResult = DiffUtil.calculateDiff(noteDiffUtil)
        noteData = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}
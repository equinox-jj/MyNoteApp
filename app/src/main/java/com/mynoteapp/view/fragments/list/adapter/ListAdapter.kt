package com.mynoteapp.view.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.databinding.ItemNoteLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var noteData = emptyList<NoteData>()

    class MyViewHolder(private val binding: ItemNoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteData: NoteData) {
            binding.noteData = noteData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNoteLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    fun setData(newData: List<NoteData>) {
        val noteDiffUtil = NoteDiffUtil(noteData, newData)
        val diffUtilResult = DiffUtil.calculateDiff(noteDiffUtil)
        this.noteData = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = noteData[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = noteData.size

}
package com.example.todolist.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.NotesThumbnailBinding
import com.example.todolist.model.NoteDataModel
import com.example.todolist.view.Activity.NotesActivity

class notesListCustomAdapter(noteList:List<NoteDataModel>): RecyclerView.Adapter<notesListCustomAdapter.ViewHolder>() {
    private val noteLists: List<NoteDataModel> = noteList
    private var listener:OnItemClickListener?=null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding:NotesThumbnailBinding=NotesThumbnailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    fun setOnItemListener(listener: OnItemClickListener?){
        this.listener=listener
    }

    class ViewHolder(viewBind: NotesThumbnailBinding):RecyclerView.ViewHolder(viewBind.root) {
        val binding=viewBind
        fun clickPosition(position: Int,listener: OnItemClickListener?){
            binding.noteRoot.setOnClickListener {
                listener?.onItemClick(position)
            }
        }
        fun onDelete(position: Int,listener: OnItemClickListener?){
            binding.thumbnailToolbar.inflateMenu(R.menu.more)
            binding.thumbnailToolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.delete-> {
                        listener?.onItemDelete(position)
                        true
                    }
                    else -> {false}
                }
            }
        }
      
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.headerText.text= noteLists[position].mainHeading
        holder.binding.secondTitle.text= noteLists[position].subHeading
        holder.clickPosition(position,listener)
        holder.onDelete(position,listener)
    }
    override fun getItemCount(): Int {
        return noteLists.size
    }
}
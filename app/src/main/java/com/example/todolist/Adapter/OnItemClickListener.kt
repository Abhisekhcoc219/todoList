package com.example.todolist.Adapter

import android.icu.text.Transliterator.Position
import com.example.todolist.model.NoteDataModel

interface OnItemClickListener {
    fun onItemClick(position: Int)
}
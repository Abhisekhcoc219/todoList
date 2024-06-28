package com.example.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteDataModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "Headings")val mainHeading:String?,
    @ColumnInfo(name = "SubHeadings")val subHeading:String?,
    @ColumnInfo(name = "pinned") val isPinned:Boolean,
    @ColumnInfo(name = "backgroundColor") val backgroundColor:Int
)
{
    constructor(title:String?,noteTitle:String?,isPinned: Boolean,backgroundColor: Int) :this(0,title,noteTitle,isPinned,backgroundColor)
}

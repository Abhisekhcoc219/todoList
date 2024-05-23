package com.example.todolist.dataUtil

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteDataModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "Headings")val mainHeading:String?,
    @ColumnInfo(name = "SubHeadings")val subHeading:String?
)
{
    constructor(title:String?,noteTitle:String?) :this(0,title,noteTitle)
}

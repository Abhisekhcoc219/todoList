package com.example.todolist.dataUtil

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<NoteDataModel>>
    @Query("SELECT * FROM notes WHERE Headings LIKE '%' || :searchQuery || '%'")
    fun searchNotes(searchQuery: String?):LiveData<List<NoteDataModel>>
    @Insert
    fun insertNote(note: NoteDataModel)
    @Delete
    fun deleteNote(note: NoteDataModel)
    @Update
    fun updateNote(note: NoteDataModel)
}
package com.example.todolist.dbUtil

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.model.NoteDataModel

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<NoteDataModel>>
    @Query("SELECT * FROM notes WHERE pinned=1")
    fun getPinnedNotes():LiveData<List<NoteDataModel>>

    @Query("SELECT * FROM notes WHERE Headings LIKE '%' || :searchQuery || '%'")
    suspend fun searchNotes(searchQuery: String?):List<NoteDataModel>

    @Query("SELECT id FROM notes WHERE Headings = :text")
    suspend fun getIdByText(text: String?): Int?
    @Insert
    suspend fun insertNote(note: NoteDataModel)
    @Delete
    suspend fun deleteNote(note: NoteDataModel)

    @Update
    suspend fun updateNote(note: NoteDataModel)
}
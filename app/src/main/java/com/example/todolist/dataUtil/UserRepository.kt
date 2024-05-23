package com.example.todolist.dataUtil

import androidx.lifecycle.LiveData

class UserRepository(private val notesDao: NotesDao) {
    val allNotes: LiveData<List<NoteDataModel>> =notesDao.getAllNotes()
    suspend fun insert(noteDataModel: NoteDataModel){
        notesDao.insertNote(noteDataModel)
    }
    suspend fun updates(noteDataModel: NoteDataModel){
        notesDao.updateNote(noteDataModel)
    }
    suspend fun delete(noteDataModel: NoteDataModel){
        notesDao.updateNote(noteDataModel)
    }
    suspend fun searchNotes(searchForNotes:String?):LiveData<List<NoteDataModel>>{
        return searchForNotes?.let { notesDao.searchNotes(it) }!!
    }
}
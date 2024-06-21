package com.example.todolist.UserRepositorys

import androidx.lifecycle.LiveData
import com.example.todolist.dbUtil.NotesDao
import com.example.todolist.model.NoteDataModel
import kotlinx.coroutines.flow.Flow

class UserRepository(private val notesDao: NotesDao) {
    val allNotes: LiveData<List<NoteDataModel>> =notesDao.getAllNotes()
    val getPinnedNotes:LiveData<List<NoteDataModel>> = notesDao.getPinnedNotes()
    fun getQueryResult(query:String?): Flow<List<NoteDataModel>> {
        return notesDao.searchNotes(query)
    }
    suspend fun insert(noteDataModel: NoteDataModel){
        notesDao.insertNote(noteDataModel)
    }
    suspend fun updates(noteDataModel: NoteDataModel){
        notesDao.updateNote(noteDataModel)
    }
    suspend fun delete(noteDataModel: NoteDataModel){
        notesDao.deleteNote(noteDataModel)
    }
}
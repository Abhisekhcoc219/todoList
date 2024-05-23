package com.example.todolist.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.dataUtil.NoteDataModel
import com.example.todolist.dataUtil.NotesDao
import com.example.todolist.dataUtil.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository): ViewModel(){
  fun allNotes() =userRepository.allNotes
    suspend fun getNotes(query:String?)=userRepository.searchNotes(query)
    fun insert(noteDataModel: NoteDataModel)= CoroutineScope(Dispatchers.IO
    ).launch {
        userRepository.insert(noteDataModel)
    }
    fun update(noteDataModel: NoteDataModel)= CoroutineScope(Dispatchers.IO).launch {
        userRepository.updates(noteDataModel)
    }
}
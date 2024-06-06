package com.example.todolist.ViewModel
import androidx.lifecycle.ViewModel
import com.example.todolist.model.NoteDataModel
import com.example.todolist.UserRepositorys.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository): ViewModel(){
    var isBackPress:Boolean=false
    var isFirst:Boolean=false

  fun allNotes() =userRepository.allNotes
    suspend fun getId(text:String?): Int? =userRepository.getId(text)
    suspend fun getNotes(query:String?)=userRepository.searchNotes(query)
    fun insert(noteDataModel: NoteDataModel)= CoroutineScope(Dispatchers.IO
    ).launch {
        userRepository.insert(noteDataModel)
    }
    fun update(noteDataModel: NoteDataModel)= CoroutineScope(Dispatchers.IO).launch {
        userRepository.updates(noteDataModel)
    }
}
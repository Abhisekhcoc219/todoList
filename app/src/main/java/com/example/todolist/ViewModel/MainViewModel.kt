package com.example.todolist.ViewModel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.NoteDataModel
import com.example.todolist.UserRepositorys.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository): ViewModel(){
    var isFirst:Boolean=false
    var isPinned:Boolean=false
  fun allNotes() =userRepository.allNotes
    fun getPinNotes()=userRepository.getPinnedNotes
    suspend fun getNotes(query:String?)=userRepository.searchNotes(query)
    fun insert(noteDataModel: NoteDataModel)= CoroutineScope(Dispatchers.IO
    ).launch {
        userRepository.insert(noteDataModel)
    }
    fun update(noteDataModel: NoteDataModel)=  CoroutineScope(Dispatchers.IO).launch{
        try {
            Log.e("TAGS","p")
        userRepository.updates(noteDataModel)
        }catch (e:Exception){
            Log.e("TAGS",""+e.localizedMessage)
        }
    }
    fun delete(noteDataModel: NoteDataModel)=CoroutineScope(Dispatchers.IO).launch {
        userRepository.delete(noteDataModel)
    }
}
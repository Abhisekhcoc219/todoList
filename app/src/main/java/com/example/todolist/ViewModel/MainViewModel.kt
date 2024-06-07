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
    var isBackPress:Boolean=false
    var isFirst:Boolean=false
    var isFirstTimeInit:Boolean=false

  fun allNotes() =userRepository.allNotes
    suspend fun getId(text:String?): Int? =userRepository.getId(text)
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
}
package com.example.todolist.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    var WhichFragment=false
  fun allNotes() =userRepository.allNotes
    fun getPinNotes()=userRepository.getPinnedNotes
    private val _searchResults = MutableLiveData<List<NoteDataModel>>()
    val searchResults: LiveData<List<NoteDataModel>> get() = _searchResults
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
    fun searchNotes(query:String?){
    CoroutineScope(Dispatchers.Main).launch {
        query?.let {
            userRepository.getQueryResult(it).collect{ it ->
                _searchResults.value=it
            }
        }
    }
    }
}
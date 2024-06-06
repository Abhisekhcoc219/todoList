package com.example.todolist.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.UserRepositorys.UserRepository

@Suppress("UNCHECKED_CAST")
class MainModelViewFactory(val userRepository: UserRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainViewModel(userRepository) as T
    }
}
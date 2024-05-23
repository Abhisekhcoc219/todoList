package com.example.todolist.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.HomePage
import com.example.todolist.dataUtil.UserRepository

@Suppress("UNCHECKED_CAST")
class mainModelViewFactory(val userRepository: UserRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainViewModel(userRepository) as T
    }
}
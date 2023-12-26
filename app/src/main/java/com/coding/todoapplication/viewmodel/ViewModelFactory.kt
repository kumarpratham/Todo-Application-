package com.coding.todoapplication.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coding.todoapplication.database.TaskRepository

class ViewModelFactory(val app : Application,private val repository: TaskRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TodoViewModel::class.java)){
            return TodoViewModel(app,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }

}
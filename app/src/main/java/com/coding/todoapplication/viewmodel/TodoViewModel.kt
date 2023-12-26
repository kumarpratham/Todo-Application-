package com.coding.todoapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.coding.todoapplication.database.TaskRepository
import com.coding.todoapplication.model.Task
import kotlinx.coroutines.launch

class TodoViewModel(app : Application, private val taskRepository: TaskRepository) : AndroidViewModel(app) {
    fun addTask(task : Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }
    fun deleteTask(task: Task) = viewModelScope.launch {
        taskRepository.deleteTask(task)
    }
    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }
    fun getAllTasks() = taskRepository.gatAllTasks()

    fun getTaskByType(query : String?) = taskRepository.getTaskByType(query)

    fun searchTask(query : String?) = taskRepository.searchTask(query)



}
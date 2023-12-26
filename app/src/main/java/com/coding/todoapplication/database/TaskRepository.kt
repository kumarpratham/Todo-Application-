package com.coding.todoapplication.database

import com.coding.todo.database.TaskDatabase
import com.coding.todoapplication.model.Task

class TaskRepository(private val db : TaskDatabase) {
    suspend fun insertTask(task : Task) = db.getTaskDAO().insertTask(task)
    suspend fun updateTask(task: Task) = db.getTaskDAO().updateTask(task)
    suspend fun deleteTask(task: Task) = db.getTaskDAO().deleteTask(task)
    fun gatAllTasks() = db.getTaskDAO().getAllTask()
    fun getTaskByType(query : String?) = db.getTaskDAO().getAllTaskByType(query)
    fun searchTask(query: String?) = db.getTaskDAO().searchTask(query)
}
package com.coding.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.coding.todoapplication.database.TaskDAO
import com.coding.todoapplication.model.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskDAO() : TaskDAO

    companion object {
        @Volatile
        private var instance : TaskDatabase ?= null
        private val LOCK = Any()
        operator fun invoke(context : Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_db"
            ).build()
    }
}
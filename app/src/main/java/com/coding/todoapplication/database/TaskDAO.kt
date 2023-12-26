package com.coding.todoapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coding.todoapplication.model.Task


@Dao
interface TaskDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task : Task)

    @Update
    suspend fun updateTask(task : Task)

    @Delete
    suspend fun deleteTask(task : Task)

    @Query("SELECT * FROM tasks ORDER BY task_id DESC")
    fun getAllTask() : LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE task_type = :query ORDER BY task_id DESC")
    fun getAllTaskByType(query: String?) : LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE task_title LIKE :query OR task_type LIKE :query")
    fun searchTask(query: String?) : LiveData<List<Task>>

}
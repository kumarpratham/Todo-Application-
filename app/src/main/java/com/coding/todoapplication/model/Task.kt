package com.coding.todoapplication.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tasks")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    var id : Int,
    @ColumnInfo(name = "task_title")
    var title : String,
    @ColumnInfo(name = "task_created")
    var date : String,
    @ColumnInfo(name = "task_type")
    var type : String

) : Parcelable

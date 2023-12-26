package com.coding.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.coding.todo.database.TaskDatabase
import com.coding.todoapplication.database.TaskRepository
import com.coding.todoapplication.databinding.ActivityMainBinding
import com.coding.todoapplication.viewmodel.TodoViewModel
import com.coding.todoapplication.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var viewModel: TodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val todoRepository = TaskRepository(TaskDatabase(this))
        val viewModelFactory = ViewModelFactory(application,todoRepository)
        viewModel = ViewModelProvider(this,viewModelFactory)[TodoViewModel::class.java]

    }
}
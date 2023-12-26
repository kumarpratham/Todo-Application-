package com.coding.todoapplication

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import com.coding.todoapplication.databinding.FragmentNewTaskBinding
import com.coding.todoapplication.model.Task
import com.coding.todoapplication.viewmodel.TodoViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewTaskFragment : Fragment(R.layout.fragment_new_task) {

    private var _binding : FragmentNewTaskBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: TodoViewModel
    private lateinit var mView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.Type))
        binding.autoCompleteTextView.setAdapter(adapter)
        mView = view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_new_task,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> {
                saveTask(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun saveTask(mView: View) {
        val taskTitle = binding.etTitle.text.toString().trim()
        val taskType = binding.autoCompleteTextView.text.toString().trim()
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDate = LocalDate.now()
            val dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            currentDate.format(dateFormat).toString()
        } else {
            ""
        }

        if (taskTitle.isNotEmpty()) {
            val task = Task(0,taskTitle,date,taskType)
            viewModel.addTask(task)
            Toast.makeText(requireContext(),"Task is added, Well done!!", Toast.LENGTH_LONG).show()
            mView.findNavController().navigate(R.id.action_newTaskFragment_to_homeFragment)
            mView.findNavController().popBackStack(R.id.newTaskFragment, false)
        }else {
            Toast.makeText(requireContext(),"Please enter the task title", Toast.LENGTH_LONG).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
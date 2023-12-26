package com.coding.todoapplication

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.coding.todoapplication.databinding.FragmentUpdateTaskBinding
import com.coding.todoapplication.model.Task
import com.coding.todoapplication.viewmodel.TodoViewModel

class UpdateTaskFragment : Fragment(R.layout.fragment_update_task) {
    private var _binding : FragmentUpdateTaskBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: TodoViewModel
    private lateinit var currentTask : Task
    private val args : UpdateTaskFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateTaskBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        currentTask = args.task!!
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.Type))
        binding.autoCompleteTextView.setAdapter(adapter)

        binding.etTitle.setText(currentTask.title)
        binding.autoCompleteTextView.setText(currentTask.type,false)

        binding.fabBtnDone.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val type = binding.autoCompleteTextView.text.toString().trim()

            if(title.isNotEmpty()) {
                val task = Task(currentTask.id,title,currentTask.date,type)
                viewModel.updateTask(task)
                Toast.makeText(context,"Note Updated Successfully" , Toast.LENGTH_LONG).show()
                view.findNavController().navigate(R.id.action_updateTaskFragment_to_homeFragment)
            }
            else {
                Toast.makeText(context,"Please enter note Title", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun deleteTask() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete the task?")
            setPositiveButton("Delete"){_,_ ->
                viewModel.deleteTask(currentTask)
                view?.findNavController()?.navigate(R.id.action_updateTaskFragment_to_homeFragment)
                view?.findNavController()?.popBackStack(R.id.updateTaskFragment,false)
                Toast.makeText(requireContext(),"Task deleted",Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_task,menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete -> {
                deleteTask()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
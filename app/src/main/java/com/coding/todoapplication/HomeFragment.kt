package com.coding.todoapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.todoapplication.adapter.RecyclerAdapter
import com.coding.todoapplication.databinding.FragmentHomeBinding
import com.coding.todoapplication.model.Task
import com.coding.todoapplication.viewmodel.TodoViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {
    private var _binding : FragmentHomeBinding?= null
    private val binding get() = _binding!!
    private lateinit var adapter : RecyclerAdapter
    private lateinit var viewModel : TodoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
//        viewModel.addTask(Task(0,"hello world6","10/10/2000","Default"))
        setUpRecyclerView()
        binding.fabBtnAdd.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newTaskFragment)
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        searchNote(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchNote(newText)
        }
        return true
    }

    private fun searchNote(query: String?){
        val searchQuery = "%$query"
        viewModel.searchTask(searchQuery).observe(
            this
        ) { tasks ->
            adapter.differ.submitList(tasks)
        }
    }
    private fun setUpRecyclerView() {
        binding.recyclerView.visibility = View.VISIBLE
        adapter = RecyclerAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter


        activity?.let {
            viewModel.getAllTasks().observe(
                viewLifecycleOwner
            ) { tasks ->
                adapter.differ.submitList(tasks)
                updateUI(tasks)
            }
        }
    }

    private fun updateUI(tasks : List<Task>?) {
        if(tasks != null) {
            if (tasks.isNotEmpty()){
                binding.recyclerView.visibility = View.VISIBLE
                binding.noTaskMsgBox.visibility = View.GONE
            }else {
                binding.recyclerView.visibility = View.GONE
                binding.noTaskMsgBox.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)
        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.setOnQueryTextListener(this)
    }

}
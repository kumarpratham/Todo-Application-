package com.coding.todoapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coding.todoapplication.HomeFragmentDirections
import com.coding.todoapplication.R
import com.coding.todoapplication.UpdateTaskFragment
import com.coding.todoapplication.UpdateTaskFragmentDirections
import com.coding.todoapplication.databinding.ItemLayoutBinding
import com.coding.todoapplication.model.Task
import java.util.Random

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(var itemBinding:ItemLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id ==newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.type == newItem.type &&
                    oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTask = differ.currentList[position]
        holder.itemBinding.tvTitle.text = currentTask.title
        holder.itemBinding.tvDate.text = currentTask.date
        holder.itemBinding.tvType.text = currentTask.type
        val random = Random()
        val color = Color.argb(255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256),
        )

        holder.itemBinding.vColor.setBackgroundColor(color)
//
        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateTaskFragment(currentTask)
            it.findNavController().navigate(direction)
        }
    }
}
package com.brahatksingh.to_doapp.Fragments.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.brahatksingh.to_doapp.Data.Models.Priority
import com.brahatksingh.to_doapp.Data.Models.ToDoData
import com.brahatksingh.to_doapp.R

class ListAdapter() : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var dataList = emptyList<ToDoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val tempView = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return ListViewHolder(tempView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.title.text = dataList[position].title
        holder.description.text = dataList[position].description
        when(dataList[position].priority){
            Priority.LOW -> {
                holder.priority.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
            }
            Priority.HIGH -> {
                holder.priority.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.red))
            }
            else -> {
                holder.priority.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.yellow))
            }
        }
        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(tempList : List<ToDoData>) {
        val diffutil = ToDoDiffUtil(dataList,tempList)
        val tododiffutilresult = DiffUtil.calculateDiff(diffutil)
        dataList = tempList
        tododiffutilresult.dispatchUpdatesTo(this)
    }

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.findViewById<TextView>(R.id.item_title_txt)
        val description = itemView.findViewById<TextView>(R.id.item_description_txt)
        val priority = itemView.findViewById<CardView>(R.id.item_priority_indicator)
    }
}
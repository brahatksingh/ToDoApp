package com.brahatksingh.to_doapp.Fragments.List

import androidx.recyclerview.widget.DiffUtil
import com.brahatksingh.to_doapp.Data.Models.ToDoData

class ToDoDiffUtil (private val oldlist : List<ToDoData>,private val newlist : List<ToDoData>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
         return oldlist.size
    }

    override fun getNewListSize(): Int {
         return newlist.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition] === newlist[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldlist[oldItemPosition].id == newlist[newItemPosition].id)
                && (oldlist[oldItemPosition].title == newlist[newItemPosition].title)
                && (oldlist[oldItemPosition].description == newlist[newItemPosition].description)
                && (oldlist[oldItemPosition].priority == newlist[newItemPosition].priority)
    }

}
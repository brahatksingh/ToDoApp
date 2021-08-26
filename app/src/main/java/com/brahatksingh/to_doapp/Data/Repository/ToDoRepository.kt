package com.brahatksingh.to_doapp.Data.Repository

import androidx.lifecycle.LiveData
import com.brahatksingh.to_doapp.Data.Models.ToDoData
import com.brahatksingh.to_doapp.Data.ToDoDAO

class ToDoRepository(private val tododao : ToDoDAO) {

    val getAllData : LiveData<List<ToDoData>> = tododao.getAllData()
    val dataSortedByHighPriority :  LiveData<List<ToDoData>> = tododao.sortByHighPriority()
    val dataSortedByLowPriority:  LiveData<List<ToDoData>> = tododao.sortByLowPriority()

    suspend fun insertData(toDoDataTemp: ToDoData) {
        tododao.insertData(toDoDataTemp)
    }

    suspend fun updateData(toDoData : ToDoData) {
        tododao.updateData(toDoData)
    }

    suspend fun deleteData(toDoData: ToDoData) {
        tododao.deleteData(toDoData)
    }

    suspend fun deleteAll() = tododao.deleteAll()

    fun searchDatabase(gotstring : String) : LiveData<List<ToDoData>> {
        return tododao.search(gotstring)
    }
}
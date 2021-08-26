package com.brahatksingh.to_doapp.Data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.brahatksingh.to_doapp.Data.Models.ToDoData
import com.brahatksingh.to_doapp.Data.Repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDAO = ToDoDatabase.getToDoDatabase(application).getToDoDao()
    private val repository :  ToDoRepository
    val dataSortedByHighPriority :  LiveData<List<ToDoData>>
    val dataSortedByLowPriority:  LiveData<List<ToDoData>>

    val getAllData : LiveData<List<ToDoData>>

    init {
        repository = ToDoRepository(toDoDAO)
        getAllData = repository.getAllData
        dataSortedByHighPriority = repository.dataSortedByHighPriority
        dataSortedByLowPriority = repository.dataSortedByLowPriority
    }

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun delteData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(toDoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(gotstring : String) : LiveData<List<ToDoData>> {
            return repository.searchDatabase(gotstring)

    }

}
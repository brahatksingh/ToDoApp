package com.brahatksingh.to_doapp.Fragments.Add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.brahatksingh.to_doapp.Data.Models.Priority
import com.brahatksingh.to_doapp.Data.Models.ToDoData
import com.brahatksingh.to_doapp.Data.ToDoViewModel
import com.brahatksingh.to_doapp.Fragments.ShareViewModel
import com.brahatksingh.to_doapp.R
import com.brahatksingh.to_doapp.databinding.FragmentAddBinding


class AddFragment : Fragment() {
    private val toDoViewModel : ToDoViewModel by viewModels()
    private val sharedViewModel : ShareViewModel by viewModels()
    private lateinit var binding : FragmentAddBinding
    private lateinit var navController : NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add,container,false)
        setHasOptionsMenu(true)
        navController = findNavController()
        binding.prioritiesSpinner.onItemSelectedListener = sharedViewModel.listener
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_frag_menu_add) {
            insertDataInDB()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataInDB() {
        val madetitle = binding.etvTitle.text.toString()
        val madepriority = binding.prioritiesSpinner.selectedItem.toString()
        val madedescription = binding.etDescription.text.toString()

        if(madetitle.isEmpty() || madedescription.isEmpty()) {
            Toast.makeText(context,"Invalid Title or Description",Toast.LENGTH_SHORT).show()
            return
        }
        val newtodo = ToDoData(0,madetitle,makePriority(madepriority),madedescription)
        toDoViewModel.insertData(newtodo)
        Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show()
        navController.navigate(R.id.action_addFragment_to_listFragment)
    }

    private fun makePriority(gotPriority : String) : Priority{

        return when(gotPriority) {
            "High Priority" -> {Priority.HIGH}
            "Medium Priority" -> {Priority.MEDIUM}
            else -> {Priority.LOW}
        }

    }
}
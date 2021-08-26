package com.brahatksingh.to_doapp.Fragments.Update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.brahatksingh.to_doapp.Data.Models.Priority
import com.brahatksingh.to_doapp.Data.Models.ToDoData
import com.brahatksingh.to_doapp.Data.ToDoViewModel
import com.brahatksingh.to_doapp.Fragments.ShareViewModel
import com.brahatksingh.to_doapp.R
import com.brahatksingh.to_doapp.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    lateinit var binding : FragmentUpdateBinding
    private val sharedViewModel : ShareViewModel by viewModels()
    private val mViewModel : ToDoViewModel by viewModels()
    lateinit private var navController : NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_update,container,false)
        setHasOptionsMenu(true)
        binding.updaterEtvTitle.setText(args.currentItem.title)
        binding.updaterEtDescription.setText(args.currentItem.description)
        binding.updaterPrioritiesSpinner.setSelection(makePriority(args.currentItem.priority))
        binding.updaterPrioritiesSpinner.onItemSelectedListener = sharedViewModel.listener
        navController = findNavController()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.update_fragment_menu_save) {
            updateItem()
        }
        else if(item.itemId == R.id.update_fragment_menu_delete) {
            deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makePriority(temp : Priority) : Int{
         return when(temp) {
             Priority.HIGH -> {
                 2
             }
             Priority.MEDIUM -> {
                 1
             }
             else -> {
                 0
             }
         }
    }

    private fun updateItem() {
        val title = binding.updaterEtvTitle.text.toString()
        val description = binding.updaterEtDescription.text.toString()
        val priority = when(binding.updaterPrioritiesSpinner.selectedItem.toString()) {
            "High Priority" -> {
                Priority.HIGH
            }
            "Medium Priority" -> {
                Priority.MEDIUM
            }
            else -> {
                Priority.LOW
            }
        }
        if(title.isEmpty() || description.isEmpty()) {
            Toast.makeText(context,"Empty Title or Description",Toast.LENGTH_SHORT).show()
            return
        }
        val updatedToDo = ToDoData(args.currentItem.id,title,priority,description)
        mViewModel.updateData(updatedToDo)
        Toast.makeText(context,"Updated",Toast.LENGTH_SHORT).show()
        navController.navigate(R.id.action_updateFragment_to_listFragment)
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") {one,two ->
            mViewModel.delteData(args.currentItem)
            Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { one,two ->

        }
        builder.setTitle("Delete this ToDo")
        builder.setMessage("Are you sure you want to delete this ToDo?")
        builder.create().show()
    }

}
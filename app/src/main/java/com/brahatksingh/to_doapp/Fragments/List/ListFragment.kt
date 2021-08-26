package com.brahatksingh.to_doapp.Fragments.List

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.brahatksingh.to_doapp.Data.Models.ToDoData
import com.brahatksingh.to_doapp.Data.ToDoViewModel
import com.brahatksingh.to_doapp.R
import com.brahatksingh.to_doapp.Utils.observeOnce
import com.brahatksingh.to_doapp.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(),SearchView.OnQueryTextListener {
    private val toDoViewModel : ToDoViewModel by viewModels()
    lateinit var binding : FragmentListBinding
    lateinit var navController : NavController
    private val adapter : ListAdapter by lazy { ListAdapter() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate (inflater,R.layout.fragment_list,container,false)
        navController = findNavController()
        setHasOptionsMenu(true)

        val rv = binding.recyclerView
        rv.adapter = adapter
        rv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        rv.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        swipeToDelete(rv)
        toDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            dataList ->
            adapter.setData(dataList)
            if(dataList.isEmpty()) {
                binding.noToDoToShowImage.visibility = View.VISIBLE
                binding.noToDoToShowText.visibility = View.VISIBLE
            }
            else {
                binding.noToDoToShowImage.visibility = View.GONE
                binding.noToDoToShowText.visibility = View.GONE
            }
        })

        binding.floatingActionButton.setOnClickListener {
             navController.navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.listLayoutContainer.setOnClickListener {
            navController.navigate(R.id.action_listFragment_to_updateFragment)
        }

        hideKeyboard()

        return binding.root

    }

    private fun hideKeyboard() {
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity?.currentFocus
        currentFocusedView?.let{
            inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.list_fragment_menu,menu)

        val searchmenu = menu.findItem(R.id.menu_search)
        val searchview = searchmenu.actionView as? SearchView
        searchview?.isSubmitButtonEnabled = true
        searchview?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menu_delete_all -> {
                val builder = AlertDialog.Builder(context)
                builder.setPositiveButton("Yes") {one,two ->
                    toDoViewModel.deleteAll()
                    Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("No") { one,two ->

                }
                builder.setTitle("Delete all ToDos")
                builder.setMessage("Are you sure you want to delete all ToDos?")
                builder.create().show()
            }
            R.id.menu_priority_high -> {
                toDoViewModel.dataSortedByHighPriority.observe(viewLifecycleOwner, Observer {
                    newdatalist ->
                    adapter.setData(newdatalist)
                })
            }
            R.id.menu_priority_low -> {
                toDoViewModel.dataSortedByLowPriority.observe(viewLifecycleOwner, Observer {
                    adapter.setData(it)
                })
            }
            else -> {
                // Nothing
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun swipeToDelete(rv : RecyclerView) {
        val swipeCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemtodelete = adapter.dataList[viewHolder.adapterPosition]
                toDoViewModel.delteData(itemtodelete)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreDeletedData(viewHolder.itemView,itemtodelete)
            }
        }
        val itemTH = ItemTouchHelper(swipeCallback)
        itemTH.attachToRecyclerView(rv)
    }

    fun restoreDeletedData(view : View ,todo : ToDoData) {
        val snackbar = Snackbar.make(view,"Deleted ${todo.title}",Snackbar.LENGTH_LONG)
        snackbar.setAction("UNDO"){
            toDoViewModel.insertData(todo)
        }
        snackbar.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchDb(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null) {
            searchDb(query)
        }
        return true
    }

    private fun searchDb(query: String) {
        var searchQuery = "%${query}%"

        toDoViewModel.searchDatabase(searchQuery).observeOnce(viewLifecycleOwner, Observer { newdatalist ->
            newdatalist?.let {
                adapter.setData(it)
            }
        })

    }
}
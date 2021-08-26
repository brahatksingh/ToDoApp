package com.brahatksingh.to_doapp.Fragments

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.brahatksingh.to_doapp.R

class ShareViewModel(application: Application) : AndroidViewModel(application) {

    val listener : AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view : View?, position : Int, id : Long) {
            when(position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,R.color.green))
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,R.color.yellow))
                }
                2-> {
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,R.color.red))
                }
                else -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,R.color.red))
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}

    }

}
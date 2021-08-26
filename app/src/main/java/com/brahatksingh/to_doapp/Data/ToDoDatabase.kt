package com.brahatksingh.to_doapp.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brahatksingh.to_doapp.Data.Models.ToDoData

@Database(entities = [ToDoData::class],version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun getToDoDao(): ToDoDAO

    companion object {
        @Volatile
        private var INSTANCE : ToDoDatabase? = null

        fun getToDoDatabase(context : Context ) : ToDoDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context,ToDoDatabase::class.java,"todo_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }


}
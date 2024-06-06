package com.example.todolist.dbUtil

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.model.NoteDataModel

@Database(entities = [NoteDataModel::class], version = 1, exportSchema = false)
abstract class NoteListDatabase:RoomDatabase() {
    abstract fun noteDao():NotesDao
    companion object{
        @Volatile
        private var INSTANCE:NoteListDatabase? = null
        fun getDatabase(context: Context):NoteListDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteListDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
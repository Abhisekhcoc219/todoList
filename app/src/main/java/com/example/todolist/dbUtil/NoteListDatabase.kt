package com.example.todolist.dbUtil

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.R
import com.example.todolist.model.NoteDataModel

@Database(entities = [NoteDataModel::class], version = 3, exportSchema = false)
abstract class NoteListDatabase:RoomDatabase() {
    abstract fun noteDao():NotesDao
    companion   object{
        val MIGRATION1_2=object:Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE notes ADD COLUMN pinned INTEGER NOT NULL DEFAULT 0")
            }
        }

        val MIGRATION2_3=object:Migration(2,3){
            override fun migrate(db: SupportSQLiteDatabase) {
                val defaultValue:Int= R.color.lightRed
                db.execSQL("ALTER TABLE notes ADD COLUMN backgroundColor INTEGER NOT NULL DEFAULT $defaultValue")
            }
        }
        @Volatile
        private var INSTANCE:NoteListDatabase? = null
        fun getDatabase(context: Context):NoteListDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteListDatabase::class.java,
                    "note_database"
                ).setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .addMigrations(MIGRATION1_2,MIGRATION2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
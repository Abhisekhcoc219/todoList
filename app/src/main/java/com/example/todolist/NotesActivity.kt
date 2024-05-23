package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist.ViewModel.MainViewModel
import com.example.todolist.ViewModel.mainModelViewFactory
import com.example.todolist.dataUtil.NoteDataModel
import com.example.todolist.dataUtil.NoteListDatabase
import com.example.todolist.dataUtil.UserRepository
import com.example.todolist.databinding.ActivityNotesBinding

@Suppress("CAST_NEVER_SUCCEEDS")
class NotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotesBinding
    private val mainViewModel: MainViewModel by viewModels {
        mainModelViewFactory(UserRepository(NoteListDatabase.getDatabase(this).noteDao()))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.notesToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.backButton.setOnClickListener {
            startActivity(Intent(this,HomePage::class.java))
            finish()
        }
        binding.Save.setOnClickListener {
            val Title=binding.title.getText().toString()
            val NoteTitle=binding.titleNotes.getText().toString()
            if(Title.isNotEmpty()){
                val userNote: NoteDataModel =NoteDataModel(Title,NoteTitle)
                mainViewModel.insert(userNote)}
        }
        binding.Show.setOnClickListener {
          mainViewModel.allNotes().observe(this){
              val allNotes=it
              allNotes.get(0).mainHeading?.let { it1 -> Log.d("TAGS", it1) }
          }
//            for (i in 0 until allNotes.size){
//                Log.d("TAGS", allNotes.get(i).toString())
//            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.more,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.delete-> {
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }
}
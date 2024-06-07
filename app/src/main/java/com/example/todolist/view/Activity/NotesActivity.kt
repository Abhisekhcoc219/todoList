package com.example.todolist.view.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.todolist.R
import com.example.todolist.ViewModel.MainViewModel
import com.example.todolist.ViewModel.MainModelViewFactory
import com.example.todolist.model.NoteDataModel
import com.example.todolist.dbUtil.NoteListDatabase
import com.example.todolist.UserRepositorys.UserRepository
import com.example.todolist.databinding.ActivityNotesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("CAST_NEVER_SUCCEEDS")
class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainModelViewFactory(UserRepository(NoteListDatabase.getDatabase(this).noteDao()))
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
        mainViewModel.isFirst=intent.getBooleanExtra("overWrite",false)
        if(mainViewModel.isFirst){
            val positions:Int=intent.getIntExtra("pos",0)
            mainViewModel.allNotes().observe(this, Observer {
                binding.mainTitle.text= it[positions].mainHeading?.toEditable()
                binding.titleNotes.text=it[positions].subHeading?.toEditable()
                Log.d("TAGS","YES ${it[positions].id} "+positions)
            })
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.backButton.setOnClickListener {
            startActivity(Intent(this@NotesActivity,HomePageActivity::class.java))
            finish()
        }

        binding.shows.setOnClickListener {
            mainViewModel.allNotes().observe(this, Observer {
                Log.e("tags",it.toString())
            })
          mainViewModel.update(NoteDataModel("hjdskhdjksh","hjsdkhsdkj"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        StoreData()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.more,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.delete -> {
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }

    private fun StoreData(){
        val Title=binding.mainTitle.getText().toString()
        val NoteTitle=binding.titleNotes.getText().toString()
        if(!mainViewModel.isFirst){
            if(Title.isNotEmpty() || NoteTitle.isNotEmpty()){
                mainViewModel.insert(NoteDataModel(Title,NoteTitle))
            }
            else{
//                Toast.makeText(this, "not created", Toast.LENGTH_SHORT).show()
            }
        }
        else{
//            Log.e("TAGS","YEESSS"+binding.mainTitle.text.toString())
            val firstText:String? = binding.mainTitle.text.toString()
            val secondText:String? = binding.titleNotes.text.toString()
            val pos=intent.getIntExtra("pos",0)
            mainViewModel.update(NoteDataModel(pos+1,firstText, secondText))
        }

    }
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}
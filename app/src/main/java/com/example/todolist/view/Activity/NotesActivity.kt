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
            Log.e("DEBUGSS",""+intent.getBooleanExtra("isSearch",false))
            if(intent.getBooleanExtra("isSearch",false)){
                binding.mainTitle.text=intent.getStringExtra("mainHeading")?.toEditable()
                binding.titleNotes.text=intent.getStringExtra("subHeading")?.toEditable()
                isCheckPinnedForSearchFirstFragment()
            }
            else if(intent.getBooleanExtra("isSearchPinnedFragment",false)){
                binding.mainTitle.text=intent.getStringExtra("mainHeadingPinnedFragment")?.toEditable()
                binding.titleNotes.text=intent.getStringExtra("subHeadingPinnedFragment")?.toEditable()
                isCheckPinnedForSearchSecondFragment()
            }
            else{
                if(intent.getBooleanExtra("first",false)){
                    mainViewModel.allNotes().observe(this, Observer {
                        if(it.isNotEmpty()){
                            binding.mainTitle.text= it[positions].mainHeading?.toEditable()
                            binding.titleNotes.text=it[positions].subHeading?.toEditable()
                            Log.d("TAGS","YES ${it[positions].id} "+positions)
                            isCheckPinnedForFirstFragment()
                        }
                    })}
                else{
                    mainViewModel.getPinNotes().observe(this, Observer {
                        if(it.isNotEmpty()){
                            binding.mainTitle.text= it[positions].mainHeading?.toEditable()
                            binding.titleNotes.text=it[positions].subHeading?.toEditable()
                            isCheckPinnedForSecondFragment()
                        }
                    })
                }
            }
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.pinButton.setOnClickListener {
            addToPinned()
        }
    }

    private fun isCheckPinnedForSearchSecondFragment(){
        if(intent.getBooleanExtra("isPinnedFragment",false))
        {
            binding.pinButton.text=getString(R.string.unpinned)
        }
        else
        {
            binding.pinButton.text=getString(R.string.pinned)
        }
    }
    private fun isCheckPinnedForSearchFirstFragment(){
        if(intent.getBooleanExtra("isPinned",false))
        {
            binding.pinButton.text=getString(R.string.unpinned)
        }
        else
        {
            binding.pinButton.text=getString(R.string.pinned)
        }
    }
    private fun isCheckPinnedForFirstFragment(){
        val pos=intent.getIntExtra("pos",0)
        val list=mainViewModel.allNotes().value?.get(pos)
//        Log.e("TAGS",list.toString())
        if(list?.isPinned!!){
            binding.pinButton.text=getString(R.string.unpinned)
        }
        else{
            binding.pinButton.text=getString(R.string.pinned)
        }
    }


    private fun isCheckPinnedForSecondFragment(){
        val pos=intent.getIntExtra("pos",0)
        val list=mainViewModel.getPinNotes().value?.get(pos)
//      Log.e("TAGS",list.toString())

        if(list?.isPinned!!){
            binding.pinButton.text=getString(R.string.unpinned)
        }
        else{
            binding.pinButton.text=getString(R.string.pinned)
        }
    }
    private fun addToPinned(){
        val pos=intent.getIntExtra("pos",0)
        val list=mainViewModel.allNotes().value?.get(pos)
        Log.e("TAGS",list.toString())
        if(getString(R.string.pinned)===binding.pinButton.text){
            binding.pinButton.text=getString(R.string.unpinned)
            mainViewModel.isPinned=true
        }
        else if(getString(R.string.unpinned)===binding.pinButton.text)
        {
            binding.pinButton.text=getString(R.string.pinned)
            mainViewModel.isPinned=false
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
                val id=intent.getIntExtra("listId",0)
                if(intent.getBooleanExtra("isSearch",false)){
                    mainViewModel.delete(NoteDataModel(intent.getIntExtra("searchId",0),binding.mainTitle.text.toString(),binding.titleNotes.text.toString(),mainViewModel.isPinned,intent.getIntExtra("backgroundColorFragment1",R.color.lightGreen)))
                }
                else if(intent.getBooleanExtra("isSearchPinnedFragment",false)){
                    mainViewModel.delete(NoteDataModel(intent.getIntExtra("searchIdPinnedFragment",0),binding.mainTitle.text.toString(),binding.titleNotes.text.toString(),mainViewModel.isPinned,intent.getIntExtra("backgroundColorFragment2",R.color.lightGreen)))
                }
                else{
                mainViewModel.delete(NoteDataModel(id,binding.mainTitle.text.toString(),binding.titleNotes.text.toString(),mainViewModel.isPinned,intent.getIntExtra("color",R.color.lightGreen)))
                }
                finish()
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
                if(Title.isEmpty() && NoteTitle.isEmpty()){
                    Toast.makeText(this, "note is empty please add notes", Toast.LENGTH_SHORT).show()
                }
                else{
                mainViewModel.insert(NoteDataModel(Title,NoteTitle,mainViewModel.isPinned,mainViewModel.getRandomColor()))}
            }
            else{
//                Toast.makeText(this, "not created", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            val firstText: String = binding.mainTitle.text.toString()
            val secondText: String = binding.titleNotes.text.toString()
            val pos=intent.getIntExtra("listId",0)
            if(!intent.getBooleanExtra("first",true)){
                mainViewModel.isPinned=true
            }
            if(getString(R.string.pinned)===binding.pinButton.text){
                mainViewModel.isPinned=false
            }
            else if(getString(R.string.unpinned)===binding.pinButton.text){
                mainViewModel.isPinned=true
            }
            if(firstText.isEmpty() && NoteTitle.isEmpty()){
                Toast.makeText(this, "note is empty please add notes", Toast.LENGTH_SHORT).show()
            }
            else{
                if(intent.getBooleanExtra("isSearch",false)){
                   mainViewModel.update(NoteDataModel(intent.getIntExtra("searchId",0),firstText, secondText,mainViewModel.isPinned,intent.getIntExtra("backgroundColorFragment1",R.color.lightGreen)))
                }
                else if (intent.getBooleanExtra("isSearchPinnedFragment",false)){
                    mainViewModel.update(NoteDataModel(intent.getIntExtra("searchIdPinnedFragment",0),firstText, secondText,mainViewModel.isPinned,intent.getIntExtra("backgroundColorFragment2",R.color.lightGreen)))
                }
                else{
                mainViewModel.update(NoteDataModel(pos,firstText, secondText,mainViewModel.isPinned,intent.getIntExtra("color",R.color.lightGreen)))
                }
            }
        }
    }
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}
package com.example.todolist.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Adapter.OnItemClickListener
import com.example.todolist.Adapter.notesListCustomAdapter
import com.example.todolist.UserRepositorys.UserRepository
import com.example.todolist.ViewModel.MainModelViewFactory
import com.example.todolist.ViewModel.MainViewModel
import com.example.todolist.databinding.FragmentAllListBinding
import com.example.todolist.dbUtil.NoteListDatabase
import com.example.todolist.model.NoteDataModel
import com.example.todolist.view.Activity.NotesActivity
class AllListFragment: Fragment(),OnItemClickListener {
    private lateinit var _binding: FragmentAllListBinding
    private lateinit var factory:MainModelViewFactory
    private lateinit var viewModel:MainViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        factory= MainModelViewFactory(UserRepository(NoteListDatabase.getDatabase(context).noteDao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAllListBinding.inflate(inflater, container, false)
        val viewModelStore = ViewModelStore()
        val viewModelProvider = ViewModelProvider(viewModelStore, factory)
        viewModel= viewModelProvider[MainViewModel::class.java]
        _binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        viewModel.allNotes().observe(viewLifecycleOwner, Observer {
            val customAdapter:notesListCustomAdapter=notesListCustomAdapter(it)
            customAdapter.setOnItemListener(this)
            _binding.recyclerView.adapter=customAdapter
        })
        _binding.addButton.setOnClickListener {
            startActivity(Intent(getActivity(), NotesActivity::class.java))
        }
        return _binding.root
    }

    override fun onStop() {
        super.onStop()
//        Toast.makeText(context, "s yes", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
//        Toast.makeText(context, "d yes", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        val listData=viewModel.allNotes().value?.get(position)
        if (listData != null) {
            val intent:Intent=Intent(requireContext(),NotesActivity::class.java)
            intent.putExtra("pos",position)
            intent.putExtra("overWrite",true)
            startActivity(intent)
        }
    }
}
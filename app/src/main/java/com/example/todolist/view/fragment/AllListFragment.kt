package com.example.todolist.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.todolist.Adapter.SearchFragment
import com.example.todolist.Adapter.notesListCustomAdapter
import com.example.todolist.UserRepositorys.UserRepository
import com.example.todolist.ViewModel.MainModelViewFactory
import com.example.todolist.ViewModel.MainViewModel
import com.example.todolist.databinding.FragmentAllListBinding
import com.example.todolist.dbUtil.NoteListDatabase
import com.example.todolist.model.NoteDataModel
import com.example.todolist.view.Activity.NotesActivity
class AllListFragment: Fragment(),OnItemClickListener,SearchFragment {
    private lateinit var _binding: FragmentAllListBinding
    private lateinit var factory:MainModelViewFactory
    private lateinit var viewModel:MainViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        factory= MainModelViewFactory(UserRepository(NoteListDatabase.getDatabase(context).noteDao()))
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAllListBinding.inflate(inflater, container, false)
        val viewModelStore = ViewModelStore()
        val viewModelProvider = ViewModelProvider(viewModelStore, factory)
        viewModel= viewModelProvider[MainViewModel::class.java]
        _binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        if(!viewModel.search.value!!){
            viewModel.allNotes().observe(viewLifecycleOwner, Observer {
                Log.e("DEBUGSS","hello")
                val customAdapter:notesListCustomAdapter=notesListCustomAdapter(requireContext(),it)
                customAdapter.setOnItemListener(this)
                _binding.recyclerView.adapter=customAdapter
                customAdapter.notifyDataSetChanged()
            })
        }
        _binding.addButton.setOnClickListener {
            startActivity(Intent(getActivity(), NotesActivity::class.java))
        }
        viewModel.allNotes().observe(viewLifecycleOwner, Observer {
            Log.e("TAGS",it.toString())
        })
        return _binding.root
    }

    override fun onItemClick(position: Int) {
        if(viewModel.search.value!!){
            val searchData=viewModel.searchResults.value?.get(position)
            Log.e("DEBUGSS","$searchData")
            if (searchData != null) {
                val intent:Intent=Intent(requireContext(),NotesActivity::class.java)
                intent.putExtra("position",position)
                intent.putExtra("first",true)
                intent.putExtra("searchId",searchData.id)
                intent.putExtra("mainHeading",searchData.mainHeading)
                intent.putExtra("subHeading",searchData.subHeading)
                intent.putExtra("isPinned",searchData.isPinned)
                intent.putExtra("overWrite",true)
                intent.putExtra("isSearch",true)
                startActivity(intent)
            }
        }
        else{
            val listData=viewModel.allNotes().value?.get(position)
            if (listData != null) {
                Log.e("DEBUGSS","allnotes")
                val intent:Intent=Intent(requireContext(),NotesActivity::class.java)
                intent.putExtra("pos",position)
                intent.putExtra("first",true)
                intent.putExtra("listId",listData.id)
                intent.putExtra("overWrite",true)
                startActivity(intent)
            }
        }
        viewModel.setValueInSearch(false)
    }

    override fun onItemDelete(position: Int) {
        Log.e("TAGS","entery delete")
        if(viewModel.search.value!!)
        {
            Log.e("DEBUGSS","delete operation")

            if(position>=0) {
                val searchNotes = viewModel.searchResults.value?.get(position)
                viewModel.delete(
                    NoteDataModel(
                        searchNotes!!.id,
                        searchNotes!!.mainHeading,
                        searchNotes!!.subHeading,
                        searchNotes!!.isPinned
                    )
                )
            }
        }
        else
        {
            val listNotes= viewModel.allNotes().value?.get(position)
            viewModel.delete(NoteDataModel(listNotes!!.id,listNotes!!.mainHeading, listNotes!!.subHeading,listNotes!!.isPinned))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun searchList(query: String) {
     Log.e("TAGS","es $query")
        viewModel.searchResults.observe(viewLifecycleOwner, Observer {
            val customAdapter:notesListCustomAdapter=notesListCustomAdapter(requireContext(),it)
            customAdapter.setOnItemListener(this)
            _binding.recyclerView.adapter=customAdapter
            customAdapter.notifyDataSetChanged()
        })
        viewModel.searchNotes(query)
    }
    override fun isCompleteSearch(isSearching: Boolean) {
        viewModel.setValueInSearch(isSearching)
    }


    override fun onStart() {
        super.onStart()
        Log.e("DEBUGSS","onStart")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("DEBUGSS","onDestroyView")
    }

    override fun onPause() {
        super.onPause()
        Log.e("DEBUGSS","onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.e("DEBUGSS","onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.e("DEBUGSS","onResume")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("DEBUGSS","onDestroy")
    }
}
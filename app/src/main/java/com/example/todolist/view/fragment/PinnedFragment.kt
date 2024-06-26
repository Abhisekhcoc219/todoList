package com.example.todolist.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Adapter.OnItemClickListener
import com.example.todolist.Adapter.SearchFragment
import com.example.todolist.Adapter.notesListCustomAdapter
import com.example.todolist.UserRepositorys.UserRepository
import com.example.todolist.ViewModel.MainModelViewFactory
import com.example.todolist.ViewModel.MainViewModel
import com.example.todolist.databinding.FragmentPinnedBinding
import com.example.todolist.dbUtil.NoteListDatabase
import com.example.todolist.model.NoteDataModel
import com.example.todolist.view.Activity.NotesActivity

/**
 * A simple [Fragment] subclass.
 * Use the [PinnedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PinnedFragment: Fragment(),OnItemClickListener,SearchFragment {
    private lateinit var binding: FragmentPinnedBinding
    // TODO: Rename and change types of parameters
    private lateinit var factory: MainModelViewFactory
    private lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        factory= MainModelViewFactory(UserRepository(NoteListDatabase.getDatabase(context).noteDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPinnedBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val viewModelStore= ViewModelStore()
        viewModel=ViewModelProvider(viewModelStore,factory).get(MainViewModel::class.java)
        binding.pinnedRecycler.layoutManager=LinearLayoutManager(requireContext())
        viewModel.getPinNotes().observe(viewLifecycleOwner, Observer {
            val NoteListCustomAdapter=notesListCustomAdapter(requireContext(),it)
            NoteListCustomAdapter.setOnItemListener(this)
            binding.pinnedRecycler.adapter=NoteListCustomAdapter
        })
        viewModel.WhichFragment=true
        binding.addButton.setOnClickListener {
            startActivity(Intent(getActivity(), NotesActivity::class.java))
        }
        return binding.root
    }

    override fun onItemClick(position: Int) {
        val listData=viewModel.getPinNotes().value?.get(position)
        if (listData != null) {
            val intent:Intent=Intent(requireContext(),NotesActivity::class.java)
            intent.putExtra("pos",position)
            intent.putExtra("first",false)
            intent.putExtra("listId",listData.id)
            intent.putExtra("overWrite",true)
            startActivity(intent)
        }
    }

    override fun onItemDelete(position: Int) {
        val listNotes= viewModel.getPinNotes().value?.get(position)
        viewModel.delete(NoteDataModel(listNotes!!.id,listNotes!!.mainHeading, listNotes!!.subHeading,listNotes!!.isPinned))
    }

    override fun searchList(query: String) {
        viewModel.searchResults.observe(viewLifecycleOwner, Observer {
            val NoteListCustomAdapter=notesListCustomAdapter(requireContext(),it)
            NoteListCustomAdapter.setOnItemListener(this)
            binding.pinnedRecycler.adapter=NoteListCustomAdapter
            Log.e("TAGS",it.toString())
        })
        viewModel.searchNotes(query)
        Log.e("TAGS","pinned $query")
    }

    override fun isCompleteSearch(isSearching: Boolean) {
    }
}
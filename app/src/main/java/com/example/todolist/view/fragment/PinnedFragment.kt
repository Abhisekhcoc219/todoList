package com.example.todolist.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.databinding.FragmentPinnedBinding
import com.example.todolist.view.Activity.NotesActivity

/**
 * A simple [Fragment] subclass.
 * Use the [PinnedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PinnedFragment: Fragment() {
    private lateinit var binding: FragmentPinnedBinding
    // TODO: Rename and change types of parameters
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPinnedBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.addButton.setOnClickListener {
            startActivity(Intent(getActivity(), NotesActivity::class.java))
        }
        return binding.root
    }
}
package com.example.todolist.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todolist.view.fragment.AllListFragment
import com.example.todolist.view.fragment.PinnedFragment

class ViewPagerAdapter(val context: Context, fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int =2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllListFragment()
            1 -> PinnedFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
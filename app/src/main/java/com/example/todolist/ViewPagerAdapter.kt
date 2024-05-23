package com.example.todolist

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity:FragmentActivity,val list:List<Fragment>):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int =list.size
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllListFragment()
            1 -> PinnedFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
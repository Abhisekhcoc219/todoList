package com.example.todolist

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist.customView.StraightLineView
import com.example.todolist.dataUtil.NoteListDatabase
import com.example.todolist.dataUtil.UserRepository
import com.example.todolist.databinding.ActivityHomePageBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomePage : AppCompatActivity() {
    private lateinit var straightLineView: StraightLineView
    private lateinit var homePageBinding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        homePageBinding= ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(homePageBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        straightLineView=homePageBinding.line
        setSupportActionBar(homePageBinding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val fragmentList= listOf(AllListFragment(),PinnedFragment())
        homePageBinding.viewPager.adapter=ViewPagerAdapter(this,fragmentList)
        TabLayoutMediator(homePageBinding.tabLayout,homePageBinding.viewPager){tab,position->
//            val customTab: View =layoutInflater.inflate(R.layout.custom_tab,null)
//            val tabText=customTab.findViewById<TextView>(R.id.tab_text)
            tab.text = when(position){
                0->"All"
                else->"Pinned"
            }
//            tab.customView=customTab
        }.attach()
        homePageBinding.tabLayout.addOnTabSelectedListener(object :
            com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@HomePage,R.color.black))
                val textView = tab?.view?.findViewById<TextView>(android.R.id.text1)
                textView?.setTextColor(ContextCompat.getColor(this@HomePage, R.color.white))
            }

            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@HomePage,R.color.white))
                val textView = tab?.view?.findViewById<TextView>(android.R.id.text1)
                textView?.setTextColor(ContextCompat.getColor(this@HomePage, R.color.light_black))
            }

            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@HomePage,R.color.black))
                val textView = tab?.view?.findViewById<TextView>(android.R.id.text1)
                textView?.setTextColor(ContextCompat.getColor(this@HomePage, R.color.white))
            }
        })
        homePageBinding.tabLayout.getTabAt(0)?.select()

    }
}
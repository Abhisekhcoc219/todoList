package com.example.todolist.view.Activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todolist.Adapter.SearchFragment
import com.example.todolist.view.fragment.AllListFragment
import com.example.todolist.view.fragment.PinnedFragment
import com.example.todolist.R
import com.example.todolist.Adapter.ViewPagerAdapter
import com.example.todolist.UserRepositorys.UserRepository
import com.example.todolist.ViewModel.MainModelViewFactory
import com.example.todolist.ViewModel.MainViewModel
import com.example.todolist.customView.StraightLineView
import com.example.todolist.databinding.ActivityHomePageBinding
import com.example.todolist.dbUtil.NoteListDatabase
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageActivity : AppCompatActivity() {
    private lateinit var straightLineView: StraightLineView
    private lateinit var homePageBinding: ActivityHomePageBinding
    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    private val mainViewModel: MainViewModel by viewModels {
        MainModelViewFactory(UserRepository(NoteListDatabase.getDatabase(this).noteDao()))
    }

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
        homePageBinding.viewPager.adapter= ViewPagerAdapter(this,this)
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
                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@HomePageActivity, R.color.black))
                val textView = tab?.view?.findViewById<TextView>(android.R.id.text1)
                textView?.setTextColor(ContextCompat.getColor(this@HomePageActivity, R.color.white))
            }

            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@HomePageActivity, R.color.white))
                val textView = tab?.view?.findViewById<TextView>(android.R.id.text1)
                textView?.setTextColor(ContextCompat.getColor(this@HomePageActivity, R.color.light_black))
            }

            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@HomePageActivity, R.color.black))
                val textView = tab?.view?.findViewById<TextView>(android.R.id.text1)
                textView?.setTextColor(ContextCompat.getColor(this@HomePageActivity, R.color.white))
            }
        })

        homePageBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle the submitted query here, e.g., perform a search
                    query?.let {
                        currentFragmentSearch(it)
                    }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // This is optional, handle query text changes if needed
                searchRunnable?.let { handler.removeCallbacks(it) }
                searchRunnable= Runnable {
                    newText?.let {
                        currentFragmentSearch(it)
                    }
                }
                handler.postDelayed(searchRunnable!!,300)
                return true
            }
        })
        homePageBinding.searchView.setOnCloseListener {
            currentFragmentSearch("")
            false
        }
        homePageBinding.tabLayout.getTabAt(0)?.select()

    }
    private fun currentFragmentSearch(query: String) {
        val fragment = supportFragmentManager.findFragmentByTag("f${homePageBinding.viewPager.currentItem}")
        if (fragment is SearchFragment) {
            runOnUiThread {
            fragment.searchList(query)
                fragment.isCompleteSearch(true)
            }
        }
    }
}
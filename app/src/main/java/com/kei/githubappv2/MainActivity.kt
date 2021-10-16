package com.kei.githubappv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kei.githubappv2.adapter.UserAdapter
import com.kei.githubappv2.databinding.ActivityMainBinding
import com.kei.githubappv2.model.Users
import com.kei.githubappv2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var listUser : ArrayList<Users>
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setUserAdapter()
        showProgressBar(true)
        setViewModel()
    }

    private fun setViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                MainViewModel::class.java
        )
        if (mainViewModel.getListUser().value == null){
            mainViewModel.setListUser(this, "")
        }
        mainViewModel.getListUser().observe(this, {users ->
            if (users != null){
                listUser = users
                userAdapter.setData(listUser)
                showProgressBar(false)
            }
        })
    }

    private fun showProgressBar(b: Boolean) {
        if (b){
            mainBinding.pbMain.visibility = View.VISIBLE
        }else{
            mainBinding.pbMain.visibility = View.GONE
        }
    }

    private fun setUserAdapter() {
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        mainBinding.rvMain.apply {
            setHasFixedSize(true)
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

        }
    }
}
package com.binar.githubuserapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var rvUsers: RecyclerView
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rvUsers = findViewById(R.id.rv_users)
        rvUsers.setHasFixedSize(true)

        list.addAll(listUsers)
        showRecyclerList()
    }

    private val listUsers: ArrayList<User>
        @SuppressLint("Recycle")
        get() {
            val dataName = resources.getStringArray(R.array.name)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
            val datalocation = resources.getStringArray(R.array.location)
            val datarepository = resources.getStringArray(R.array.repository)
            val datafollowers = resources.getStringArray(R.array.followers)
            val datafollowing = resources.getStringArray(R.array.following)

            val listUser = ArrayList<User>()
            for (i in dataName.indices) {
                val user = User(
                    dataName[i],
                    dataUsername[i],
                    dataCompany[i],
                    dataAvatar.getResourceId(i, -1),
                    datalocation[i],
                    datarepository[i],
                    datafollowers[i],
                    datafollowing[i]
                )
                listUser.add(user)
            }
            return listUser
        }


    private fun showSelectedUser(user: User) {
        Toast.makeText(this, "Lihat Detail " + user.name, Toast.LENGTH_SHORT).show()

        val pilihDetail = Intent(this@MainActivity, DetailUser::class.java)
        pilihDetail.putExtra(DetailUser.DETAIL_USER, user)
        startActivity(pilihDetail)
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvUsers.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = ListUserAdapter(list)
        rvUsers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)

            }
        })
    }
}




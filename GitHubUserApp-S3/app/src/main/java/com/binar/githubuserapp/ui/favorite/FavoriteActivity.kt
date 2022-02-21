package com.binar.githubuserapp.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.githubuserapp.R
import com.binar.githubuserapp.data.local.FavoriteUser
import com.binar.githubuserapp.data.model.User
import com.binar.githubuserapp.databinding.ActivityFavoriteBinding
import com.binar.githubuserapp.ui.detail.DetailUserActivity
import com.binar.githubuserapp.ui.main.ListUserAdapter

class FavoriteActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private lateinit var adapter: ListUserAdapter
    private val viewModel by viewModels<FavoriteViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClik(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        binding.apply {
            rvUserFavorite.setHasFixedSize(true)
            rvUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFavorite.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url,
                user.html_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}
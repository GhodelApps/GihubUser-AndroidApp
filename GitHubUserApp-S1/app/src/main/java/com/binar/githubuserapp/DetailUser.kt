package com.binar.githubuserapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.githubuserapp.databinding.ActivityDetailUserBinding


class DetailUser : AppCompatActivity() {

    private val binding by lazy { ActivityDetailUserBinding.inflate(layoutInflater) }

    companion object {
        const val DETAIL_USER = "detail_user"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val users = intent.getParcelableExtra<User>(DETAIL_USER) as User

        binding.ivAvatar.setImageResource(users.avatar)
        binding.tvDetailName.text = users.name
        binding.tvDetailUsername.text = "@${users.username}"
        binding.tvDetailLocation.text = "Location : ${users.location}"
        binding.tvDetailCompany.text = "Company : ${users.company}"
        binding.tvDetailRepo.text = """
            ${users.repository}
            Repository
        """.trimIndent()
        binding.tvDetailFollowers.text = """
            ${users.followers} 
            Follower
        """.trimIndent()
        binding.tvDetailFollowing.text = """
            ${users.following} 
            Following
        """.trimIndent()

    }
}


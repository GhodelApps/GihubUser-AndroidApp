package com.binar.githubuserapp.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.binar.githubuserapp.R
import com.binar.githubuserapp.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator


class DetailUser : AppCompatActivity() {
//    companion object {
//        const val EXTRA_USERNAME = "extra username"
//
//        @StringRes
//        private val TAB_TITLE = intArrayOf(R.string.tab1, R.string.tab2)
//    }

    private val binding by lazy { ActivityDetailUserBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<DetailUserViewModel>()

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        if (username != null) {
            viewModel.setUserDetail(username)
        }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    tvDetailName.text = it.name
                    tvDetailUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    tvRepo.text = "${it.public_repos} Repository"
                    tvLocation.text = it.location
                    tvCompany.text = it.company

                    Glide.with(this@DetailUser)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .into(ivAvatar)
                }
            }
        })

        viewModel.getToastObserver().observe(this,
            { message: String? ->
                Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            })

        val viewPagerAdapter = ViewPagerAdapter(this, bundle)
        binding.apply {
            viewPager2.adapter = viewPagerAdapter
            TabLayoutMediator(tabsLayout, viewPager2) { tab, position ->
                tab.text = resources.getString(TAB_TITLE[position])
            }.attach()
        }

    }

    companion object {
        const val EXTRA_USERNAME = "extra username"

        @StringRes
        private val TAB_TITLE = intArrayOf(R.string.tab1, R.string.tab2)
    }
}


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
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Suppress("LocalVariableName")
class DetailUserActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailUserBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<DetailUserViewModel>()

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL_AVATAR)
        val url = intent.getStringExtra(EXTRA_URL)
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
                    tvFollowers.text = resources.getString(R.string.followers, it.followers)
                    tvFollowing.text = resources.getString(R.string.following, it.following)
                    tvRepo.text = resources.getString(R.string.repository, it.public_repos)
                    tvLocation.text = it.location
                    tvCompany.text = it.company

                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .placeholder(R.drawable.loading_github)
                        .error(R.drawable.error_avatar)
                        .into(ivAvatar)
                }
            }
        })
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.addFavorite(username, id, avatarUrl, url)
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = _isChecked
        }

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
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL_AVATAR = "extra_url_avatar"
        const val EXTRA_URL = "extra_url"

        @StringRes
        private val TAB_TITLE = intArrayOf(R.string.tab1, R.string.tab2)
    }
}


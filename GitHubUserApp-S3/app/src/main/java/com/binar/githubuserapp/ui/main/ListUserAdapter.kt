package com.binar.githubuserapp.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.githubuserapp.R
import com.binar.githubuserapp.data.model.User
import com.binar.githubuserapp.databinding.ItemRowUserBinding
import com.bumptech.glide.Glide
import kotlin.collections.ArrayList


class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {
    private val list = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClik(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .placeholder(R.drawable.loading_github)
                    .error(R.drawable.error_avatar)
                    .into(imgItemPhoto)
                tvItemUsername.text = user.login
                tvItemId.text = "id: ${user.id}"
                tvItemUrl.text = user.html_url

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClik(data: User)
    }
}

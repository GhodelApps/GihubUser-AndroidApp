package com.binar.githubuserapp.data.model


data class DetailUserResponse(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val following: Int,
    val followers: Int,
    val public_repos: Int,
    val location: String,
    val company: String
)

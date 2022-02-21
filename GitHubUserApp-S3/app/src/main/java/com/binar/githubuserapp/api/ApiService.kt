package com.binar.githubuserapp.api

import com.binar.githubuserapp.data.model.DetailUserResponse
import com.binar.githubuserapp.data.model.User
import com.binar.githubuserapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearch(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username : String
    ):  Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username : String
    ):  Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username : String
    ):  Call<ArrayList<User>>

}
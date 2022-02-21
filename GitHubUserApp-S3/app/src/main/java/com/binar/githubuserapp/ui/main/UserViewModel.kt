package com.binar.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.binar.githubuserapp.api.Retrofit
import com.binar.githubuserapp.data.model.User
import com.binar.githubuserapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()
    private val toastMessageObserver = MutableLiveData<String?>()

    fun setSearchUsers(q: String) {
        Retrofit.apiInstance
            .getSearch(q)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    } else {
                        Log.e("UserViewModel", "onFailure: ${response.message()}")
                        toastMessageObserver.setValue(response.message())
                    }

                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("UserViewModel", "onFailure: ${t.message.toString()}")
                    toastMessageObserver.value = t.message.toString()

                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }

    fun getToastObserver(): LiveData<String?> {
        return toastMessageObserver
    }
}

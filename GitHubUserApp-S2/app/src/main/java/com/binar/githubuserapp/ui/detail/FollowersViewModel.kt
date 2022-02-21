package com.binar.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.githubuserapp.api.Retrofit
import com.binar.githubuserapp.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<User>>()
    private val toastMessageObserver = MutableLiveData<String?>()

    fun setListFollowers(username: String) {
        Retrofit.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    } else {
                        Log.e("FollowersViewModel", "onFailure ${response.message()}")
                        toastMessageObserver.setValue(response.message())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("FollowersViewModel", "onFailure ${t.message.toString()}")
                    toastMessageObserver.value = t.message.toString()
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<User>> {
        return listFollowers
    }

    fun getToastObserver(): LiveData<String?> {
        return toastMessageObserver
    }
}
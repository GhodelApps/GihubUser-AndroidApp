package com.binar.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.githubuserapp.api.Retrofit
import com.binar.githubuserapp.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()
    private val toastMessageObserver = MutableLiveData<String?>()

    fun setListFollowing(username: String) {
        Retrofit.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    } else {
                        Log.e("FollowingViewModel", "onFailure: ${response.message()}")
                        toastMessageObserver.setValue(response.message())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("FollowingViewModel", "onFailure: ${t.message.toString()}")
                    toastMessageObserver.value = t.message.toString()
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<User>> {
        return listFollowing
    }

    fun getToastObserver(): LiveData<String?> {
        return toastMessageObserver
    }
}
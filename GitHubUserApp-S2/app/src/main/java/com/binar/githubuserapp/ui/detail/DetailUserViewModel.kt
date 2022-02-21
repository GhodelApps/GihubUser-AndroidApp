package com.binar.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.githubuserapp.api.Retrofit
import com.binar.githubuserapp.data.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    val user = MutableLiveData<DetailUserResponse>()
    private val toastMessageObserver = MutableLiveData<String?>()

    fun setUserDetail(username: String) {
        Retrofit.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    } else {
                        Log.e("DetailUserViewModel", "onFailure: ${response.message()}")
                        toastMessageObserver.setValue(response.message())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e("DetailUserViewModel", "onFailure: ${t.message.toString()}")
                    toastMessageObserver.value = t.message.toString()
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun getToastObserver(): LiveData<String?> {
        return toastMessageObserver
    }
}
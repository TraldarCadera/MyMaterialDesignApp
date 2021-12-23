package com.example.mymaterialdesignapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymaterialdesignapp.BuildConfig
import com.example.mymaterialdesignapp.repository.POTDResponseData
import com.example.mymaterialdesignapp.repository.POTDRetrofitImplementation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class POTDViewModel(
    private val liveDataToObserve: MutableLiveData<POTDState> = MutableLiveData(),
    private val retrofitImpl: POTDRetrofitImplementation = POTDRetrofitImplementation()
) : ViewModel() {

    private val callback = object : Callback<POTDResponseData> {
        override fun onResponse(
            call: Call<POTDResponseData>,
            response: Response<POTDResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.value = POTDState.Success(response.body())
            } else {
                // TODO ("Поймать ошибку")
            }
        }

        override fun onFailure(call: Call<POTDResponseData>, t: Throwable) {
            // TODO("Поймать ошибку")
        }
    }

    fun getData(): LiveData<POTDState> {
        return liveDataToObserve
    }

    fun sendServerRequest() {
        liveDataToObserve.value = POTDState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataToObserve.value = POTDState.Error(Throwable("Wrong Api Key"))
        } else {
            retrofitImpl.getRetrofitImplementation().getPicture(apiKey).enqueue(callback)
        }
    }
}
package com.example.mymaterialdesignapp.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class POTDRetrofitImplementation {
    private val baseUrl = "https://api.nasa.gov/"
    fun getRetrofitImplementation(): POTDAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return retrofit.create(POTDAPI::class.java)
    }
}
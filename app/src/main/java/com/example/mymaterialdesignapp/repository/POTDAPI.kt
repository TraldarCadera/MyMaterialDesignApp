package com.example.mymaterialdesignapp.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface POTDAPI {

    @GET ("planetary/apod")
    fun getPicture(@Query("api_key")apiKey:String): Call<POTDResponseData>


}
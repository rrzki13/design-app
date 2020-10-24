package com.farazrizki13.coronaapp.api

import com.farazrizki13.coronaapp.model.CovidModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("indonesia/")
    fun getData() : Call<ArrayList<CovidModel>>
}
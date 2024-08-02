package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface raisedServiceMode {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getAllTickets")
    fun getTickets(): Call<raisedReesponse>
}
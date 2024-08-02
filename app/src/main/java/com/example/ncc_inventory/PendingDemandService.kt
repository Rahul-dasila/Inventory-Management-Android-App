package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface PendingDemandService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getPendingDemand")
    fun getPending() : Call<demandrequestedResponse>
}
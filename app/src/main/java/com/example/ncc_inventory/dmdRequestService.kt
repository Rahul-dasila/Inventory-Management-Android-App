package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface dmdRequestService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getAllDemand")
    fun getmyData() : Call<demandrequestedResponse>
}
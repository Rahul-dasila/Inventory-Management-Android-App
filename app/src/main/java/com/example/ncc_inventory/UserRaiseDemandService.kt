package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRaiseDemandService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/makeDemand")
    fun raiseDemand(@Body demand: demand): Call<userRaiseDemandResponse>
}
package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RaiseService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/raiseTicket")
    fun raise(@Body raiseReq: raiseReq): Call<raiseResponse>
}
package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface receivedUserService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getProductReceived")
    fun receivedProducts(@Body receivedUserReq: receivedUserReq) : Call<receivedUserRes>
}
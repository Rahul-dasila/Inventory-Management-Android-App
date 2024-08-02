package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface forget_pass_service {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/sendOTP")
    fun sendOtp(@Body forgetPassReq: forget_pass_req): Call<forget_pass_res>
}
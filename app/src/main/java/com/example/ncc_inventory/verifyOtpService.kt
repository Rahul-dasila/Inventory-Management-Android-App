package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface verifyOtpService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/verifyOTP")
    fun verifyOtp(@Body verifyOtpReq: verifyOtpReq): Call<verifyOtpRes>
}
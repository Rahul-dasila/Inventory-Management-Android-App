package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface savePassService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/updatePassword")
    fun savePass(@Body saveReq: saveReq) : Call<verifyOtpRes>
}
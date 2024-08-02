package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ModeratorLoginService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}moderators/login")
    fun login(@Body moderatorRequest: moderatorRequest) : Call<moderatorResponse>
}
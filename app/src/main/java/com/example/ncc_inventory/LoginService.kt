package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("${rFit.BASE_URL_PLACEHOLDER}admins/admin-login")// Replace with your actual endpoint
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
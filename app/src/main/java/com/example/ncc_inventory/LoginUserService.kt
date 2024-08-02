package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginUserService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/user-login")
    fun loginUser(@Body loginUserRequest: loginUserRequest) : Call<loginUserResponse>
}
package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/user-register")
    fun addnewUser(@Body user : User): Call<UserResponse>
}
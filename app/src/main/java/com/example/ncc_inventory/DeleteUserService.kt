package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DeleteUserService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/user-delete")
    fun deleteUser(@Body deleteUser: deleteUser): Call<EditUserResponse>
}
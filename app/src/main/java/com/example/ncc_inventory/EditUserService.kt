package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EditUserService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/user-update")
    fun editUser(@Body editedUser: EditedUser) : Call<EditUserResponse>
}
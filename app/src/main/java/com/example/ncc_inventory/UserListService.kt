package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface UserListService {
        @POST("${rFit.BASE_URL_PLACEHOLDER}users/user-getAll")
        fun getUsers(): Call<List<adapterUserItem>>
}
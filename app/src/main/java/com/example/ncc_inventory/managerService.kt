package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface managerService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}managers/manager-register") // Replace with your actual endpoint for adding products
    fun addManager(@Body manager: Manager): Call<ManagerResponse>
}
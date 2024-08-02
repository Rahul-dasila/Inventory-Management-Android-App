package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface checkStatusServiceUser {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getUserDemand")
    fun getStatus(@Body statusRequestUser: statusRequestUser):Call<statusResponseUser>
}
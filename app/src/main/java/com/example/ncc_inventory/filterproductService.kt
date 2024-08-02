package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface filterproductService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/filterProducts")
    fun getItems(@Body filterProductRequest: filterProductRequest):Call<fResponse>
}
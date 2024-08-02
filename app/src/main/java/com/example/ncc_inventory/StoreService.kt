package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface StoreService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getProductStore")
    fun getData(@Body productTypeResponse: productTypeResponse ) : Call<newStoreResponse>
}
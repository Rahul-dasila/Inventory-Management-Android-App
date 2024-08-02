package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface ProductListService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/productType-list")
    fun getProductList():Call<List<String>>
}
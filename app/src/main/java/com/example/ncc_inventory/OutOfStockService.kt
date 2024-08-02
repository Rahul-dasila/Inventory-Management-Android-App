package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface OutOfStockService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getOutOfStock")
    fun getOutofStock():Call<outofstockresponse>
}
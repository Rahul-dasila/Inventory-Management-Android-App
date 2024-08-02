package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface AllProductReportService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getProductReport")
    fun getReport():Call<allProductreportresponse>
}
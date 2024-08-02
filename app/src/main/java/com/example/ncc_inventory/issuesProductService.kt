package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface issuedProductService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/issuedReport")
    fun getProducts(): Call<issue_Product_report_res>
}
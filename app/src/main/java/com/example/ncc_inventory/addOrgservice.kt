package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface addOrgservice {
    @POST("${rFit.BASE_URL_PLACEHOLDER}companies/company-register")
    fun addOrg(@Body addOrgReq: addOrgReq) : Call<addOrgres>
}
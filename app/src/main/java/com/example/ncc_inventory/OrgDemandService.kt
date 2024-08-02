package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface OrgDemandService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getAllCompanyDemand")
    fun getComplanyDemands():Call<orgDemandResponse>
}
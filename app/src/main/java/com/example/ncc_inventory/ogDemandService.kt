package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface ogDemandService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getAllCompanyDemand")
    fun getdmds():Call<ogDemandResponse>
}
package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface issueDemandService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/unissuedDemandList")
    fun issueDemands():Call<issuedemandresponse>



    @POST("${rFit.BASE_URL_PLACEHOLDER}products/company-unissuedDemandList")
    fun issueDemandsOrg():Call<issueDemandResponseOrg>
}
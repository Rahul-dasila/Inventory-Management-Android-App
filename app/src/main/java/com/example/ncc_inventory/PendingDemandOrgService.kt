package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface PendingDemandOrgService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/getCompanyPendingDemand")
    fun pendingDemand():Call<pendingDemandResponse>

}
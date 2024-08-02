package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrgRaiseDemandService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/company-makeDemand")
    fun raiseDemnd(@Body orgRaiseDemandRequest: orgRaiseDemandRequest) : Call<orgRaiseDemandResponse>
}

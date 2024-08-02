package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface demandAcceptOrRejectService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/updateDemandStatus")
    fun updateStatus (@Body demandAcptOrRjct: demandAcptOrRjct) : Call<managerDemandAcceptOrrejectResponse>
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/updateCompanyDemandStatus")
    fun updateStatusOrg (@Body demandAcptOrRjct: demandAcptOrRjct) : Call<managerDemandAcceptOrrejectResponse>
}
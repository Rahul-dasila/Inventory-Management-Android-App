package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AssignService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/assignSingleCompanyProduct")
    fun assign(@Body responseAssign: responseAssign) : Call<assignRequest>
}
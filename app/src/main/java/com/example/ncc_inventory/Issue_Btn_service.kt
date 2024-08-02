package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Issue_Btn_service {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/assignSingleProduct")
    fun assign(@Body issueBtnRequest: issue_btn_request) : Call<issue_btn_response>
}
package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface orgListService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/company-getAll")
    fun getOrglist():Call<orglistRes>
}
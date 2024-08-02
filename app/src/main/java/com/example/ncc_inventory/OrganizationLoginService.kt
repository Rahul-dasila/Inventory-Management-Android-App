package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrganizationLoginService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/company-login")
    fun orgLogin(@Body orgLoginRequest: orgLoginRequest):Call<orgLoginResponse>

}
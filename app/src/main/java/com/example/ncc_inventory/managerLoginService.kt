package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface managerLoginService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}managers/manager-login")
    fun managerLogin(@Body managerloginrequest: managerloginrequest) : Call<managerLoginResponse>
}
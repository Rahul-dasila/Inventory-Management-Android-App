package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DeleteManagerService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}managers/manager-delete")
    fun deleteManager(@Body deleteManager: deleteManager) : Call<ManagerResponse>
}
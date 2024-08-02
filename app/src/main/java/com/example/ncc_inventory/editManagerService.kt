package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface editManagerService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}managers/manager-update")
    fun editManager(@Body managerEdit: managerEdit) : Call<ManagerResponse>
}
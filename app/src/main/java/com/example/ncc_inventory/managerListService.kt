package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface managerListService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}managers/manager-getAll")
    fun getManagers(): Call<List<managerDataclassFor>>

}
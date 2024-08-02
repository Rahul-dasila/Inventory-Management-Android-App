package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AddITPersonService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}moderators/register")
    fun addIt_person(@Body addItResponse: addItResponse ):Call<additra>
}
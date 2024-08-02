package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.POST

interface ModeratorListService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/moderator-getAll")
    fun getModeratorList():Call<List<moderators>>
}
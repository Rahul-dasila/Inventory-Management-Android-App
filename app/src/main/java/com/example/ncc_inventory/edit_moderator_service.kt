package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface edit_moderator_service {
    @POST("${rFit.BASE_URL_PLACEHOLDER}moderators/moderator-update")
    fun edituser(@Body editModeratorRequest: edit_moderator_request) : Call<edit_moderator_response>

    @POST("${rFit.BASE_URL_PLACEHOLDER}moderators/moderator-delete")
    fun deleteModerator(@Body deleteModerator: deleteModerator) : Call<deleteModeratorResponsee>

}
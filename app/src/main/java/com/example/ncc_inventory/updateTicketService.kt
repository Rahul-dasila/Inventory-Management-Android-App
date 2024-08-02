package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface updateTicketService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}products/updateTicket")
    fun updatetct(@Body updateticketreq: updateticketreq): Call<updateticketResponse>
}
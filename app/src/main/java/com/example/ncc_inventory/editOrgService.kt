package com.example.ncc_inventory

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface editOrgService {
    @POST("${rFit.BASE_URL_PLACEHOLDER}users/company-update")
    fun editOrg(@Body editOrgReq: editOrgReq):Call<editorgRes>

    @POST("${rFit.BASE_URL_PLACEHOLDER}users/company-delete")
    fun deleteOrg(@Body deleteOrgreq: deleteOrgreq ) : Call<deleteOrgresponse>
}
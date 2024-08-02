package com.example.ncc_inventory

data class storeResponse(val success: Boolean, val totalProducts : String ,val HELD : String, val ISSUED : String , val SERVICEABLE : String, val UNSERVICEABLE : String , val BER : String)
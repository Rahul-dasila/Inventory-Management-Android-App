package com.example.ncc_inventory

data class managerLoginResponse(val success : Boolean,val managerData: managerData,val token: String? = null)

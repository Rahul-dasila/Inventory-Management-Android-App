package com.example.ncc_inventory

data class loginUserResponse(val success: Boolean, val message: String, val user: myUser?,val token: String? = null)

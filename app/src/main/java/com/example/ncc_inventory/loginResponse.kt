package com.example.ncc_inventory

data class LoginResponse(val success: Boolean, val message: String, val token: String? = null, val admin: Admin?)
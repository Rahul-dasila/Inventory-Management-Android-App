package com.example.ncc_inventory

data class formattedDemands(
    val productId : String,
    val productType : String,
    val productName : String,
    val productModel : String,
    val productBrand : String,
    val status : String,
    val updatedAt : String,
    val ticketStatus : String
)

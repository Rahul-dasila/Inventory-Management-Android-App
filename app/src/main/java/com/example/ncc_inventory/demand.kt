package com.example.ncc_inventory

data class demand(
    val demandId: String,
    val userId : String,
    val designation : String,
    val productType :String,
    val productName : String,
    val productModel : String,
    val productBrand : String,
    val additionalDetail : String,
    val productQuantity : Int
)

package com.example.ncc_inventory

data class requestStatus( val demandId : String,
    val productType : String,
    val productName : String,
    val productModel : String,
    val productBrand : String,
    val productQuantity : Int,
    val status: String,
    val createdAt : String
)

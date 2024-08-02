package com.example.ncc_inventory

data class dmdRequestedItems(
    val createdAt : String,
    val demandId : String,
    val userId : String,
    val designation : String,
    val productName : String,
    val productType : String,
    val productModel : String,
    val productBrand : String,
    val additionalDetail : String,
    val productQuantity : Int,
    val status : String
)

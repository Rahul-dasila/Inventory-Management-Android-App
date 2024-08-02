package com.example.ncc_inventory

data class storereportRequest(
    val productType : String,
    val productName : String,
    val productModel : String,
    val productBrand : String,
    val fromDate : String,
    val toDate  :String
)

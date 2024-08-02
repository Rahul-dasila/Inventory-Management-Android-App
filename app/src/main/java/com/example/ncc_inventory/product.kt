package com.example.ncc_inventory

data class Product(
    val productType : String,
    val productId: String,
    val productName: String,
    val productBrand: String,
    val productPrice: String,
    val productModel: String, val additionalDetail: String // Assuming ADT is a string representation of date
)
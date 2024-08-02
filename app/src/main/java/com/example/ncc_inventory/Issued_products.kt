package com.example.ncc_inventory

data class Issued_products(val productId:String,
                           val productType: String,
                           val productName: String,
                           val  productModel: String,
                           val productBrand: String,
                           val productPrice: Number,
                           val additionalDetail: String,
                           val issuedTo: String)

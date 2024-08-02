package com.example.ncc_inventory

data class StoreResponse(
    val productType: String,
    val brands: List<Brand>
)

data class Brand(
    val productBrand: String,
    val products: List<Product12>
)

data class Product12(
    val productName: String,
    val models: List<String>
)

data class StoreObj(
    val type: String,
    val brands: List<Brand>
)
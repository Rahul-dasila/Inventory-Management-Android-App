package com.example.ncc_inventory

data class outOfStockDemands(
    val productType : String,
    val productBrand : String,
    val productModel : String,
    val totalDemandQuantity : Int,
    val availableQuantity : Int
)

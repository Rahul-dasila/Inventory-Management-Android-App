package com.example.ncc_inventory

data class pendingDemands(
    val demandId: String,
    val companyId: String,
    val productType: String,
    val productName: String,
    val productModel: String,
    val productBrand: String,
    val additionalDetail: String,
    val productQuantity: Int,
    val status: String,
    val createdAt: String,
)

package com.example.ncc_inventory

data class tickets(
    val ticketId : String,
    val issueType : String,
    val message  : String,
    val issuedBy : String,
    val productId : String,
    val status : String,
    val createdAt :String
)

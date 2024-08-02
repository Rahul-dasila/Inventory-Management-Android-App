package com.example.ncc_inventory

data class Manager(
    val managerId : String,
    val email : String,
    val managerName : String,
    val password : String,
    val designation : String,
    val section : String,
    val appointment : String,
    val remark : String,
    val allProductReport : Boolean,
    val demandReceived : Boolean,
    val issueProduct : Boolean
)
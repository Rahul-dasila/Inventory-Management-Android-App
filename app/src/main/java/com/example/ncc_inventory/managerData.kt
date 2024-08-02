package com.example.ncc_inventory

data class managerData(
    val managerId: String,val managerName : String,val password : String,val designation : String,val  section:String, val appointment :String, val allProductReport : Boolean,val demandReceived :Boolean,val issueProduct :Boolean
)

package com.example.ncc_inventory

data class addOrgReq(val companyId : String,
                     val companyName : String,
                     val email :String,
                     val alternativeEmail : String,
                     val contact_1 :String,val  contact_2:String,
                     val password:String)

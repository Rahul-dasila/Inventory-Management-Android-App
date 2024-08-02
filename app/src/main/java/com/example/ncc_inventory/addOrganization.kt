package com.example.ncc_inventory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class addOrganization : AppCompatActivity() {

    private lateinit var name : EditText
    private lateinit var id : EditText
    private lateinit var mail1 : EditText
    private lateinit var mail2 : EditText
    private lateinit var contact1 : EditText
    private lateinit var contact2 : EditText
    private lateinit var pass: EditText
    private lateinit var addbtn : TextView
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_organization)

        name = findViewById(R.id.orgname)
        id = findViewById(R.id.orgId)
        mail1 = findViewById(R.id.orgPemail)
        mail2 = findViewById(R.id.orgemail2)
        contact1 = findViewById(R.id.orgContact)
        contact2 = findViewById(R.id.orgContact2)
        pass = findViewById(R.id.orgpass)
        addbtn  = findViewById(R.id.orgAdd)
        retrofit = rFit.retrofit!!

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        addbtn.setOnClickListener {
            var Oname = name.text.toString()
            var Oid = id.text.toString()
            var mail = mail1.text.toString()
            var mailll = mail2.text.toString()
            var contact11 = contact1.text.toString()
            var contact22 = contact2.text.toString()
            var pss = pass.text.toString()
            if(Oname.isNullOrEmpty()||Oid.isNullOrEmpty()||contact22.isNullOrEmpty()||mail.isNullOrEmpty()||mailll.isNullOrEmpty()||contact11.isNullOrEmpty()||pss.isNullOrEmpty()){
                Toast.makeText(this@addOrganization,"Please Fill all the mandatory fields",Toast.LENGTH_SHORT).show()
            }else{
               val addOrgReq  =addOrgReq(Oid,Oname,mail,mailll,contact11,contact22,pss)
                val service = retrofit.create(addOrgservice::class.java)
                service.addOrg(addOrgReq).enqueue(object : Callback<addOrgres>{
                    override fun onResponse(call: Call<addOrgres>, response: Response<addOrgres>) {
                        if(response.isSuccessful){
                            val respo = response.body()
                            if(respo?.success == true){
                                Toast.makeText(this@addOrganization,"Added Successfull",Toast.LENGTH_SHORT).show()
                                clearFields()
                            }
                        }else{
                            Toast.makeText(this@addOrganization,"Response unsuccessful",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<addOrgres>, t: Throwable) {
                        Toast.makeText(this@addOrganization,"Some Error Occurred",Toast.LENGTH_SHORT).show()
                    }

                })
            }

        }

    }
    private fun clearFields() {
        name.text.clear()
        id.text.clear()
        mail2.text.clear()
        mail1.text.clear()
        pass.text.clear()
        contact1.text.clear()
        contact2.text.clear()
    }
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }
}
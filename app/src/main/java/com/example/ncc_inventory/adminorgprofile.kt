package com.example.ncc_inventory

import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class adminorgprofile : AppCompatActivity() {
    private lateinit var name1 : TextView
    private lateinit var name2 : TextView
    private lateinit var id : TextView
    private lateinit var c1 : TextView
    private lateinit var c2 : TextView
    private lateinit var e1 : TextView
    private lateinit var e2 : TextView
    private lateinit var back : ImageView
    private lateinit var editIcon : ImageView
    private lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminorgprofile)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        editIcon = findViewById(R.id.editIconOrg)

        val name = intent.getStringExtra("name")
        val id1 = intent.getStringExtra("id")
        val c11 = intent.getStringExtra("c1")
        val c22 = intent.getStringExtra("c2")
        val e11 = intent.getStringExtra("e1")
        val e22 = intent.getStringExtra("e2")

        retrofit = rFit.retrofit!!
        name1 = findViewById(R.id.oNameU)
        name2 = findViewById(R.id.orgName)
        id = findViewById(R.id.orgId)
        c1 = findViewById(R.id.oContact1)
        c2 = findViewById(R.id.oContact2)
        e1 = findViewById(R.id.oMail1)
        e2 = findViewById(R.id.oMail2)

        name1.text = name
        name2.text = name
        id.text = id1
        c1.text = c11
        c2.text = c22
        e1.text = e11
        e2.text = e22

       back = findViewById(R.id.oPback)
        back.setOnClickListener {
            onBackPressed()
        }

        editIcon.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.edit_organization)
            val orgName= dialog.findViewById<EditText>(R.id.orgNameE)
            val orgC1 = dialog.findViewById<EditText>(R.id.orgC1)
            val orgC2 = dialog.findViewById<EditText>(R.id.orgC2)
            val orgM1 = dialog.findViewById<EditText>(R.id.orgmail1)
            val orgM2 = dialog.findViewById<EditText>(R.id.orgmail2)
            val saveChangesButton = dialog.findViewById<TextView>(R.id.saveChangesButtonorg)
            val deleteUserButton = dialog.findViewById<TextView>(R.id.deleteorgBtn)

            orgName.setText(name)
            orgC1.setText(c11)
            orgC2.setText(c22)
            orgM1.setText(e11)
            orgM2.setText(e22)

            saveChangesButton.setOnClickListener {
                val nm = orgName.text.toString()
                val c1 = orgC1.text.toString()
                val c2 = orgC2.text.toString()
                val m1 = orgM1.text.toString()
                val m2 = orgM2.text.toString()

                val request = id1?.let { it1 -> editOrgReq(it1,nm,m1,m2,c1,c2) }
                val service = retrofit.create(editOrgService::class.java)
                if (request != null) {
                    service.editOrg(request).enqueue(object : Callback<editorgRes>{
                        override fun onResponse(
                            call: Call<editorgRes>,
                            response: Response<editorgRes>
                        ) {
                            if(response.isSuccessful){
                                val respo = response.body()
                                if(respo?.success==true) {
                                    Toast.makeText(
                                        this@adminorgprofile,
                                        "Changes Saved",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    changeProfileData(respo.company)

                                }
                            }else{
                                Toast.makeText(this@adminorgprofile,"Response failed",Toast.LENGTH_SHORT).show()

                            }
                        }

                        override fun onFailure(call: Call<editorgRes>, t: Throwable) {
                            Toast.makeText(this@adminorgprofile,"Some Error Occurred",Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }

            deleteUserButton.setOnClickListener {
             val request = id1?.let { it1 -> deleteOrgreq(it1) }
             val service = retrofit.create(editOrgService::class.java)
             (if (request != null) {
                 service.deleteOrg(request).enqueue(object : Callback<deleteOrgresponse>{
                     override fun onResponse(
                         call: Call<deleteOrgresponse>,
                         response: Response<deleteOrgresponse>
                     ) {
                         if(response.isSuccessful){
                             val respo = response.body()
                             if(respo?.success==true){
                                 Toast.makeText(this@adminorgprofile,"Organization Deleted",Toast.LENGTH_SHORT).show()
                                 onBackPressed()
                             }
                         }else{
                             Toast.makeText(this@adminorgprofile,"Response failed",Toast.LENGTH_SHORT).show()
                         }
                     }

                     override fun onFailure(call: Call<deleteOrgresponse>, t: Throwable) {
                         Toast.makeText(this@adminorgprofile,"Some Error Occurred",Toast.LENGTH_SHORT).show()

                     }

                 })
             })
            }
            dialog.show()
        }


    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        val it = Intent(this@adminorgprofile, organization_list_panel::class.java)
        startActivity(it)
        finish()
        super.onBackPressed()
    }

    private fun changeProfileData(companies: companies){
        name1.text = companies.companyName
        name2.text = companies.companyName
        c1.text = companies.contact_1
        c2.text = companies.contact_2
        e1.text = companies.email
        e2.text = companies.alternativeEmail
    }
}
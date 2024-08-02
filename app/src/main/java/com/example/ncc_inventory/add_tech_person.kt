package com.example.ncc_inventory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class add_tech_person : AppCompatActivity() {
   private lateinit var itp_name : EditText
    private lateinit var itp_id : EditText
    private lateinit var itp_desig : EditText
    private lateinit var itp_section : EditText
    private lateinit var itp_appointment : EditText
    private lateinit var itp_pass : EditText
    private lateinit var itp_remark : EditText
    private lateinit var itp_mail : EditText
    private lateinit var itp_add : TextView

    var name = ""
    var id =""
    var mail = ""
    var deg =""
    var appt = ""
    var sec = ""
    var reamrk = ""
    var pass = ""
    val retrofit = rFit.retrofit!!
    private lateinit var back :ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tech_person)
        itp_name = findViewById(R.id.itPName)
        itp_id = findViewById(R.id.itPId)
        itp_desig = findViewById(R.id.itPDesignation)
        itp_appointment = findViewById(R.id.itPAppointment)
        itp_pass = findViewById(R.id.itPPass)
        itp_section = findViewById(R.id.itPSection)
        itp_remark = findViewById(R.id.itPRemark)
        itp_add = findViewById(R.id.itPsubmitButton2)
        back = findViewById(R.id.itPBack)
        itp_mail = findViewById(R.id.itPmail)



        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        itp_add.setOnClickListener {
            itp_add.startAnimation(AnimationUtils.loadAnimation(this@add_tech_person,R.anim.click))
            name = itp_name.text.toString()
            id = itp_id.text.toString()
            deg = itp_desig.text.toString()
            appt = itp_appointment.text.toString()
            sec = itp_section.text.toString()
            reamrk = itp_remark.text.toString()
            pass = itp_pass.text.toString()
            mail = itp_mail.text.toString()
            if(name.isNullOrEmpty()||mail.isNullOrEmpty()||id.isNullOrEmpty()||deg.isNullOrEmpty()||appt.isNullOrEmpty()||sec.isNullOrEmpty()||pass.isNullOrEmpty()){
                Toast.makeText(this@add_tech_person,"Please Fill all the mandatory fields",Toast.LENGTH_SHORT).show()
            }else{
                val addItResponse  =addItResponse(id,mail,name,pass,deg,sec,appt,reamrk)
                val service = retrofit.create(AddITPersonService::class.java)
                service.addIt_person(addItResponse).enqueue(object : Callback<additra>{
                    override fun onResponse(call: Call<additra>, response: Response<additra>) {
                        if(response.isSuccessful){
                            val respo = response.body()
                            if(respo?.success==true){
                                Toast.makeText(this@add_tech_person,"Added Successfully",Toast.LENGTH_SHORT).show()
                                clearFields()
                            }
                        }else{
                            Toast.makeText(this@add_tech_person,"Null",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<additra>, t: Throwable) {
                        Toast.makeText(this@add_tech_person,"Some error occurred",Toast.LENGTH_SHORT).show()
                    }

                })

            }
        }

    }

    private fun clearFields() {
        itp_name.text.clear()
        itp_mail.text.clear()
        itp_id.text.clear()
        itp_desig.text.clear()
        itp_appointment.text.clear()
        itp_section.text.clear()
        itp_remark.text.clear()
        itp_pass.text.clear()
    }
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }
}

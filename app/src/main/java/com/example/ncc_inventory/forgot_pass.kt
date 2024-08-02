package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class forgot_pass : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<String>
    private val items =
        mutableListOf("Select one", "Admin", "Manager", "Organization", "Moderator", "User")
    private lateinit var identity: String
    private lateinit var sendbtn : TextView
    private lateinit var pb  :ProgressBar
    private lateinit var retrofit: Retrofit
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }


        identity = ""

        spinner = findViewById(R.id.ForgotPassSpinner)
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    identity = ""
                } else {
                    identity = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })

        editText = findViewById(R.id.F_email)
        sendbtn = findViewById(R.id.sendOtp_button)
        pb = findViewById(R.id.pbSo)
        pb.visibility  = View.INVISIBLE
        val click = AnimationUtils.loadAnimation(this,R.anim.click)

        val baseUrl = rFit.BASE_URL_PLACEHOLDER
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        sendbtn.setOnClickListener {
            sendbtn.startAnimation(click)
            sendbtn.visibility = View.INVISIBLE
            pb.visibility = View.VISIBLE
            val mail  = editText.text.toString()

            if(mail.isEmpty() || identity.isEmpty()){
                pb.visibility = View.INVISIBLE
                sendbtn.visibility = View.VISIBLE
                Toast.makeText(this@forgot_pass,"Please fill all fields",Toast.LENGTH_SHORT).show()
            }else{
                val request = forget_pass_req(mail,identity)
                val service = retrofit.create(forget_pass_service::class.java)
                service.sendOtp(request).enqueue(object : Callback<forget_pass_res>{
                    override fun onResponse(
                        call: Call<forget_pass_res>,
                        response: Response<forget_pass_res>
                    ) {
                        if(response.isSuccessful){
                            val respo = response.body()
                            if(respo?.success==true){
                                Toast.makeText(this@forgot_pass,"Otp sent",Toast.LENGTH_SHORT).show()
                                val it = Intent(this@forgot_pass,enter_otp_screen::class.java)
                                it.putExtra("mail",mail)
                                it.putExtra("role",identity)
                                startActivity(it)
                                pb.visibility = View.INVISIBLE
                                sendbtn.visibility = View.VISIBLE
                            }
                        }else{
                            Toast.makeText(this@forgot_pass,"Failed",Toast.LENGTH_SHORT).show()
                            pb.visibility = View.INVISIBLE
                            sendbtn.visibility = View.VISIBLE
                        }
                    }

                    override fun onFailure(call: Call<forget_pass_res>, t: Throwable) {
                        Toast.makeText(this@forgot_pass,"Response Failed",Toast.LENGTH_SHORT).show()
                        pb.visibility = View.INVISIBLE
                        sendbtn.visibility = View.VISIBLE
                    }
                })
            }
        }
    }
}
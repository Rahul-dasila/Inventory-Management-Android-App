package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class enter_otp_screen : AppCompatActivity() {
    private lateinit var verifybtn : TextView
    private lateinit var pb  : ProgressBar
    private lateinit var retrofit: Retrofit
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_otp_screen)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }


        val role = intent.getStringExtra("role")
        val mail = intent.getStringExtra("mail")
        editText = findViewById(R.id.F_Otp)
        verifybtn = findViewById(R.id.verifyBtn)
        pb = findViewById(R.id.pbVf)

        val baseUrl = rFit.BASE_URL_PLACEHOLDER
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pb.visibility = View.INVISIBLE
        val click = AnimationUtils.loadAnimation(this,R.anim.click)
        verifybtn.setOnClickListener {
            verifybtn.startAnimation(click)
            verifybtn.visibility = View.INVISIBLE
            pb.visibility = View.VISIBLE
            val otp = editText.text.toString()
            if(otp.length!=6){
                verifybtn.visibility = View.VISIBLE
                pb.visibility = View.INVISIBLE
                Toast.makeText(this@enter_otp_screen,"Enter valid otp",Toast.LENGTH_SHORT).show()
            }else{
                val request = mail?.let { it1 -> verifyOtpReq(it1,otp.toInt()) }
                val service = retrofit.create(verifyOtpService::class.java)
                if (request != null) {
                    service.verifyOtp(request).enqueue(object : Callback<verifyOtpRes>{
                        override fun onResponse(
                            call: Call<verifyOtpRes>,
                            response: Response<verifyOtpRes>
                        ) {
                            if(response.isSuccessful){
                                val respo = response.body()
                                if(respo?.success == true){
                                    Toast.makeText(this@enter_otp_screen,"Verify Successful",Toast.LENGTH_SHORT).show()
                                    val it = Intent(this@enter_otp_screen,savepass::class.java)
                                    it.putExtra("mail",mail)
                                    it.putExtra("role",role)
                                    startActivity(it)
                                    verifybtn.visibility = View.VISIBLE
                                    pb.visibility = View.INVISIBLE
                                    finish()
                                }
                            }else{
                                verifybtn.visibility = View.VISIBLE
                                pb.visibility = View.INVISIBLE
                                Toast.makeText(this@enter_otp_screen,"Verification Failed",Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<verifyOtpRes>, t: Throwable) {
                            verifybtn.visibility = View.VISIBLE
                            pb.visibility = View.INVISIBLE
                            Toast.makeText(this@enter_otp_screen,"Some Error Occurred",Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }
}
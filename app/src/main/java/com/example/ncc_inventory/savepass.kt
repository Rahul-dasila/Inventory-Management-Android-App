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

class savepass : AppCompatActivity() {
    private lateinit var savebtn : TextView
    private lateinit var pb  : ProgressBar
    private lateinit var retrofit: Retrofit
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savepass)
        savebtn = findViewById(R.id.saveBtn)
        pb = findViewById(R.id.pbSave)
        pb.visibility = View.INVISIBLE
        editText = findViewById(R.id.F_pass)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        val baseUrl = rFit.BASE_URL_PLACEHOLDER
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val mail = intent.getStringExtra("mail")
        val role = intent.getStringExtra("role")
        val click = AnimationUtils.loadAnimation(this,R.anim.click)
        savebtn.setOnClickListener {
            savebtn.startAnimation(click)
            savebtn.visibility = View.INVISIBLE
            pb.visibility = View.VISIBLE
            val np = editText.text.toString()
            if(np.isNotEmpty()){

               val request = saveReq(np,mail!!,role!!)
                val service = retrofit.create(savePassService::class.java)
                service.savePass(request).enqueue(object : Callback<verifyOtpRes>{
                    override fun onResponse(
                        call: Call<verifyOtpRes>,
                        response: Response<verifyOtpRes>
                    ) {
                        if(response.isSuccessful){
                            val respo =  response.body()
                            if(respo?.success == true){
                                pb.visibility =  View.INVISIBLE
                                savebtn.visibility = View.VISIBLE
                                Toast.makeText(this@savepass,"Password Changed",Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@savepass,loginpage::class.java))
                                finish()
                            }
                        }else{
                            pb.visibility =  View.INVISIBLE
                            savebtn.visibility = View.VISIBLE
                            Toast.makeText(this@savepass,"Response Failed",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<verifyOtpRes>, t: Throwable) {
                        pb.visibility =  View.INVISIBLE
                        savebtn.visibility = View.VISIBLE
                        Toast.makeText(this@savepass,"Some error occurred",Toast.LENGTH_SHORT).show()
                    }

                })

            }else{
                Toast.makeText(this,"Enter a valid password",Toast.LENGTH_SHORT).show()
                pb.visibility =  View.INVISIBLE
                savebtn.visibility = View.VISIBLE
            }
        }
    }
}
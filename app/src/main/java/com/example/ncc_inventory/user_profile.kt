package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView

class user_profile : AppCompatActivity() {
    private lateinit var mail : TextView
    private lateinit var desig : TextView
    private lateinit var sec : TextView
    private lateinit var appt : TextView
    private lateinit var name : TextView
    private lateinit var name2 : TextView
    private lateinit var mail2 : TextView
    private lateinit var btn : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }



        val id = intent.getStringExtra("userEmail")
        val name1 = intent.getStringExtra("userName")
        val desig1 = intent.getStringExtra("designation")
        val sec1 = intent.getStringExtra("section")
        val appt1 = intent.getStringExtra("appointment")


        mail = findViewById(R.id.MailllUG)
        mail2 = findViewById(R.id.MailUG2)
        name = findViewById(R.id.nameUG)
        name2 = findViewById(R.id.NameUG2)
        desig = findViewById(R.id.DesigUG)
        sec = findViewById(R.id.SectionUG)
        appt = findViewById(R.id.AppointmentUG)

        mail.text = id
        mail2.text = "Id:\n$id"
        name.text = name1
        name2.text = "Name\n$name1"
        desig.text = "Designation\n$desig1"
        sec.text = "Section\n$sec1"
        appt.text = "Appointment\n$appt1"


        val click = AnimationUtils.loadAnimation(this,R.anim.click)
        btn = findViewById(R.id.LogoutUG)
        btn.setOnClickListener {
            btn.startAnimation(click)
            val intent = Intent(this,loginpage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            Handler(Looper.getMainLooper()).postDelayed({
                this.startActivity(intent)
                finish()
            },500)
        }
    }
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }
}
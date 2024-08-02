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
import android.widget.ImageView
import android.widget.TextView

class moderator_profile : AppCompatActivity() {
    private lateinit var mdName1 : TextView
    private lateinit var mdName2 : TextView
    private lateinit var mdId1 : TextView
    private lateinit var mdId2 : TextView
    private lateinit var desig :  TextView
    private lateinit var section : TextView
    private lateinit var appointment : TextView
    private lateinit var backbtn : ImageView
    private lateinit var logOut :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moderator_profile)

        //For transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        backbtn = findViewById(R.id.gobackmd)
        logOut = findViewById(R.id.mDLogout)

        mdName1 = findViewById(R.id.mdName)
        mdName2 = findViewById(R.id.Namemd)
        mdId1 = findViewById(R.id.mdMail)
        mdId2 = findViewById(R.id.mdId)
        desig = findViewById(R.id.mDDesig)
        section = findViewById(R.id.mDSection)
        appointment = findViewById(R.id.mDAppointment)

        mdName1.text = intent.getStringExtra("moderatorName")
        mdName2.text = "Name:\n"+intent.getStringExtra("moderatorName")
        mdId1.text = intent.getStringExtra("moderatorId")
        mdId2.text = "Id:\n"+intent.getStringExtra("moderatorId")
        desig.text  = "Designation:\n"+intent.getStringExtra("designation")
        section.text = "Section:\n"+intent.getStringExtra("section")
        appointment.text = "Appointment:\n"+intent.getStringExtra("appointment")

        val click = AnimationUtils.loadAnimation(this,R.anim.click)
        logOut.setOnClickListener {
            logOut.startAnimation(click)
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
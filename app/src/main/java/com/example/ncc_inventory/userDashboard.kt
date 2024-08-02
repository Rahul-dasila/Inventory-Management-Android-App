package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class userDashboard : AppCompatActivity() {
    private lateinit var raiseDemand : ImageView
    private lateinit var click : Animation
    private lateinit var rcvd : ImageView
    private lateinit var cp : TextView
    private lateinit var pf : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        val name = intent.getStringExtra("userName")
        val id =  intent.getStringExtra("userEmail")
        val dsg = intent.getStringExtra("designation")
        val sc = intent.getStringExtra("section")
        val apt = intent.getStringExtra("appointment")

        cp = findViewById(R.id.cp)
        cp.text = "WElCOME, ${name}"


        rcvd = findViewById(R.id.rcvd)

        click = AnimationUtils.loadAnimation(this,R.anim.click)
        raiseDemand = findViewById(R.id.userRaiseDemand)
        raiseDemand.setOnClickListener {
            raiseDemand.startAnimation(click)
            val intent = Intent(this,userRaiseDemandPanel::class.java)
            intent.putExtra("id",id)
            intent.putExtra("desig",dsg)
            startActivity(intent)

        }
        rcvd.setOnClickListener {
            rcvd.startAnimation(click)
            val intent = Intent(this,userReceivedProductsPanel::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }

        pf = findViewById(R.id.pf)
        pf.setOnClickListener {
            pf.startAnimation(click)
            val intent = Intent(this,user_profile::class.java)
            intent.putExtra("userName",name)
            intent.putExtra("userEmail",id)
            intent.putExtra("designation",dsg)
            intent.putExtra("section",sc)
            intent.putExtra("appointment",apt)
            startActivity(intent)
        }
    }





}
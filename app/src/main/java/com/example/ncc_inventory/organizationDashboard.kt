package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class organizationDashboard : AppCompatActivity() {
    private lateinit var wlc : TextView
    private lateinit var raiseDemand : ImageView
    private lateinit var click : Animation
    private lateinit var received : ImageView
    private lateinit var pf : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organization_dashboard)



        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

       pf = findViewById(R.id.pk)
       val name =  intent.getStringExtra("name")
       val id = intent.getStringExtra("id")
       val mail = intent.getStringExtra("email")
       val contact = intent.getStringExtra("contact")

        wlc = findViewById(R.id.wlc)
        wlc.text = "Welcome, ${name}"

        raiseDemand = findViewById(R.id.orgRaiseDemand)

        click = AnimationUtils.loadAnimation(this,R.anim.click)

        raiseDemand.setOnClickListener {
            raiseDemand.startAnimation(click)
            val it = Intent(this,orgRaiseDemandPanel::class.java)
            it.putExtra("id",id)
            startActivity(it)
        }


        received  = findViewById(R.id.orgReceived)
        received.setOnClickListener {
            received.startAnimation(click)
            val it = Intent(this@organizationDashboard,userReceivedProductsPanel::class.java)
            it.putExtra("id",id)
            startActivity(it)
        }

        pf.setOnClickListener {
            val it = Intent(this@organizationDashboard,organization_profile::class.java)
            it.putExtra("name",name)
            it.putExtra("id",id)
            it.putExtra("mail",mail)
            it.putExtra("ct",contact)
            startActivity(it)
        }

    }
}
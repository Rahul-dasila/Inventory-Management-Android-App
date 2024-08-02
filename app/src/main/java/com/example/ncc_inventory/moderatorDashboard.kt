package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class moderatorDashboard : AppCompatActivity() {
    private lateinit var setname: TextView
    private lateinit var profile: ImageView
    private lateinit var issueProduct : ImageView
    private lateinit var issueO : ImageView
    private lateinit var ticket : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moderator_dashboard)

        //For transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        setname = findViewById(R.id.moderatorName)
        setname.text = "Welcome Back, " + intent.getStringExtra("moderatorName")
        var mdName2 = intent.getStringExtra("moderatorName")
        var mdId1 = intent.getStringExtra("moderatorId")
        var desig = intent.getStringExtra("designation")
        var section = intent.getStringExtra("section")
        var appointment = intent.getStringExtra("appointment")

        val click = AnimationUtils.loadAnimation(this, R.anim.click)
        profile = findViewById(R.id.profilemod)
        profile.setOnClickListener {
            profile.startAnimation(click)
            val it = Intent(this@moderatorDashboard, moderator_profile::class.java)
            it.putExtra("moderatorName", mdName2)
            it.putExtra("moderatorId", mdId1)
            it.putExtra("designation", desig)
            it.putExtra("section", section)
            it.putExtra("appointment", appointment)
            startActivity(it)


        }
        issueProduct = findViewById(R.id.categoryM)
        issueProduct.setOnClickListener {
            issueProduct.startAnimation(AnimationUtils.loadAnimation(this@moderatorDashboard,R.anim.click))
            startActivity(Intent(this@moderatorDashboard,issue_product_moderator_dashboard::class.java))
        }

        issueO = findViewById(R.id.issueO)
        issueO.setOnClickListener {
            issueO.startAnimation(AnimationUtils.loadAnimation(this@moderatorDashboard,R.anim.click))
            startActivity(Intent(this@moderatorDashboard,issueOrg::class.java))
        }

        ticket = findViewById(R.id.tick)
        ticket.setOnClickListener {
            ticket.startAnimation(AnimationUtils.loadAnimation(this@moderatorDashboard,R.anim.click))
            startActivity(Intent(this@moderatorDashboard,ticket_raised::class.java))
        }
    }
}
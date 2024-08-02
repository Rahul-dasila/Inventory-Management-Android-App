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

class organization_profile : AppCompatActivity() {
    private lateinit var name1 : TextView
    private lateinit var name2 : TextView
    private lateinit var id1 : TextView
    private lateinit var id2 : TextView
    private lateinit var ml : TextView
    private lateinit var kt : TextView
    private lateinit var lg : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organization_profile)
        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        val name = intent.getStringExtra("name")
        val mail = intent.getStringExtra("mail")
        val id = intent.getStringExtra("id")
        val ct = intent.getStringExtra("ct")

        name1 = findViewById(R.id.nameo)
        id1 = findViewById(R.id.mcv)
        name2 = findViewById(R.id.nmOG)
        id2 = findViewById(R.id.idOG)
        kt = findViewById(R.id.ctOG)
        ml = findViewById(R.id.mlOG)
        name1.text = name
        name2.text = "Name:\n$name"
        id1.text = id
        id2.text = "ID:\n$id"
        ml.text = "Email:\n$mail"
        kt.text = "Contact:\n$ct"

        val click = AnimationUtils.loadAnimation(this,R.anim.click)
        lg = findViewById(R.id.ogLogout)
        lg.setOnClickListener {
            lg.startAnimation(click)
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
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
import org.w3c.dom.Text

class profileManager : AppCompatActivity() {
    private lateinit var name : TextView
    private lateinit var Hname : TextView
    private lateinit var id : TextView
    private lateinit var hId : TextView
    private lateinit var desig : TextView
    private lateinit var section : TextView
    private lateinit var appt : TextView
    private lateinit var logout :TextView
    private lateinit var goBck : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_manager)

        name = findViewById(R.id.mpName)
        id = findViewById(R.id.mpMail)
        desig = findViewById(R.id.mpDesig)
        section = findViewById(R.id.mpSection)
        appt = findViewById(R.id.mpAppointment)

        hId = findViewById(R.id.mpMailll)
        Hname = findViewById(R.id.nameMp)


        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        val managerName = intent.getStringExtra("name")
        val managerId = intent.getStringExtra("id")
        val ManagerDesig = intent.getStringExtra("desig")
        val managerSection = intent.getStringExtra("section")
        val managerAppointment = intent.getStringExtra("appt")


        name.text = "Name:\n$managerName"
        id.text = "Id:\n$managerId"
        desig.text = "Desination:\n$ManagerDesig"
        section.text = "Section:\n$managerSection"
        appt.text = "Appointment:\n$managerAppointment"

        hId.text = managerId
        Hname.text = managerName

        logout = findViewById(R.id.mpLogout)
        logout.setOnClickListener {
            logout.startAnimation(AnimationUtils.loadAnimation(this,R.anim.click))
            val intent = Intent(this,loginpage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            Handler(Looper.getMainLooper()).postDelayed({
                this.startActivity(intent)
                finish()
            },500)
        }

        goBck = findViewById(R.id.gobackmp)
    }
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }
}
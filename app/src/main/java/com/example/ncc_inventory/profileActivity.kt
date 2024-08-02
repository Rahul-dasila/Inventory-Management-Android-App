package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NavUtils
import kotlin.math.log

class profileActivity : AppCompatActivity() {
    private lateinit var name : TextView
    private lateinit var mail : TextView
    private lateinit var mName : TextView
    private lateinit var mMail : TextView
    private lateinit var mId : TextView
    private lateinit var mRole : TextView
    private lateinit var mDepartment : TextView
    private lateinit var logout : TextView
    private lateinit var goBack : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // for transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        //set data in profile
        setValues()

        //for logging out
        logout = findViewById(R.id.logout)
        val click = AnimationUtils.loadAnimation(this,R.anim.click)
        logout.setOnClickListener {
            logout.startAnimation(click)
            val intent = Intent(this,loginpage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            Handler(Looper.getMainLooper()).postDelayed({
                this.startActivity(intent)
                finish()
            },500)
        }



        //for going back
        goBack = findViewById(R.id.goback)
        goBack.setOnClickListener {
            goBack.startAnimation(click)
            // Check if there is a parent activity specified
            val parentIntent = NavUtils.getParentActivityIntent(this)
            if (parentIntent != null) {
                // Navigate to the parent activity
                NavUtils.navigateUpTo(this, parentIntent)
            } else {
                // Handle the case where no parent activity is specified (optional)
                finish()
            }
        }

    }



    private fun setValues(){
        name = findViewById(R.id.name)
        mail = findViewById(R.id.mail)
        mName = findViewById(R.id.mName)
        mMail = findViewById(R.id.mMail)
        mId = findViewById(R.id.mId)
        mRole = findViewById(R.id.mRole)
        mDepartment = findViewById(R.id.mDepartment)


        //Setting values
       name.text =  intent.getStringExtra("userName")
       mName.text = "Name:\n${intent.getStringExtra("userName")}"
       mail.text =  intent.getStringExtra("userEmail")
       mMail.text = "Email:\n${intent.getStringExtra("userEmail")}"
       mRole.text =  "Role:\n${intent.getStringExtra("role")}"
       mId.text =    "ID:\n${intent.getStringExtra("id")}"
       mDepartment.text = "Department:\n${intent.getStringExtra("department")}"
    }
}
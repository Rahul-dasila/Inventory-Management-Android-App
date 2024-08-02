package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class managerDashboard : AppCompatActivity() {
    private lateinit var lly : LinearLayout
    private lateinit var textView: TextView
    private lateinit var managerAuthStore : ImageView
    private lateinit var click : Animation
    private lateinit var managerAddCategory : ImageView
    private lateinit var managerAdduser :ImageView
    private lateinit var pf : ImageView
    private lateinit var navBar : ImageView
    private lateinit var drawer : DrawerLayout
    private lateinit var navigationView : NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_dashboard)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        lly = findViewById(R.id.lly)
        textView = findViewById(R.id.maName)


        val check = intent.getBooleanExtra("demand",false)
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        val desig = intent.getStringExtra("desig")
        val appt = intent.getStringExtra("appt")
        val section = intent.getStringExtra("section")
        val issue = intent.getBooleanExtra("issue",false)
        val allreport = intent.getBooleanExtra("allreport",false)


        click = AnimationUtils.loadAnimation(this, R.anim.click)
        if(check==true){
            lly.visibility = View.VISIBLE
            val managerDemandReceived : ImageView = findViewById(R.id.managerDemandReceived)
            managerDemandReceived.setOnClickListener {
                managerDemandReceived.startAnimation(click)
                startActivity(Intent(this@managerDashboard,managerDemandReceivedPanel::class.java))
            }
        }
        textView.text = " Welcome, $name"

        managerAuthStore = findViewById(R.id.managerAuthStore)
        managerAuthStore.setOnClickListener {
            managerAuthStore.startAnimation(click)
            startActivity(Intent(this,AuthoraizationStrore::class.java))
        }

        managerAddCategory = findViewById(R.id.managerAddCategory)
        managerAddCategory.setOnClickListener {
            managerAddCategory.startAnimation(click)
            startActivity(Intent(this,addCategory::class.java))
        }

        managerAdduser = findViewById(R.id.managerAdduser)
        managerAdduser.setOnClickListener {
            managerAdduser.startAnimation(click)
            startActivity(Intent(this,addUser::class.java))
        }

        pf = findViewById(R.id.pf)
        pf.setOnClickListener {
            pf.startAnimation(click)
            val intent = Intent(this,profileManager::class.java)
            intent.putExtra("name",name)
            intent.putExtra("id",id)
            intent.putExtra("desig",desig)
            intent.putExtra("appt",appt)
            intent.putExtra("section",section)
            startActivity(intent)
        }

        navBar = findViewById(R.id.navImage)
        drawer = findViewById(R.id.myDrawerManager)
        navigationView = findViewById(R.id.navigation_view_manager)
        navBar.setOnClickListener {
            navBar.startAnimation(click)
            drawer.openDrawer(GravityCompat.START)
        }
        val headerView = navigationView.getHeaderView(0)
        val navHeaderName: TextView = headerView.findViewById(R.id.headerName)
        val navHeaderId: TextView = headerView.findViewById(R.id.headerId)

        navHeaderName.text = name
        navHeaderId.text = id
        val menu: Menu = navigationView.menu
        val specialItem: MenuItem = menu.findItem(R.id.issueReportM)
        specialItem.isVisible = issue
        val specialItem2 : MenuItem = menu.findItem(R.id.orgDemands)
        specialItem2.isVisible = check
        val specialItem3 : MenuItem = menu.findItem(R.id.report)
        specialItem3.isVisible = allreport

        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
            R.id.outOfStock -> {
                startActivity(Intent(this@managerDashboard,outofstock::class.java))
            }
            R.id.orgDemands ->{
                startActivity(Intent(this@managerDashboard,orgDemands_panel::class.java))
            }
            R.id.report ->{
                startActivity(Intent(this@managerDashboard,allproductreportpanel::class.java))
            }
                // Add other cases for other menu items as needed
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        })

    }
}
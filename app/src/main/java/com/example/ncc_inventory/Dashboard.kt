package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Dashboard : AppCompatActivity() {
    private lateinit var userName : TextView
    private lateinit var profile : ImageView
    private lateinit var category : ImageView
    private lateinit var addmanager31 : ImageView
    private lateinit var AddUser : ImageView
    private lateinit var store : ImageView
    private lateinit var dmdrqsted : ImageView
    private lateinit var pendingdmd : ImageView
    private lateinit var navBar : ImageView
    private lateinit var drawer : DrawerLayout
    private lateinit var navigationView : NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val click = AnimationUtils.loadAnimation(this,R.anim.click)
        userName = findViewById(R.id.adminName)
        profile = findViewById(R.id.profile)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        //getting details

        val adminName = intent.getStringExtra("userName")
        val adminEmail = intent.getStringExtra("userEmail")
        val role = intent.getStringExtra("role")
        val id = intent.getStringExtra("id")
        val department = intent.getStringExtra("department")

        //setting admin name in dashboard
        userName.text = "Welcome, $adminName"

        //profile section
        profile.setOnClickListener {
            if (adminName != null && adminEmail != null && role != null && id!=null && department!=null) {
                profileSection(profile,click,adminName,adminEmail,role,id,department)
            }
        }

        category = findViewById(R.id.category)
        category.setOnClickListener {
            category.startAnimation(click)
            startActivity(Intent(this,addCategory::class.java))
        }

        addmanager31 = findViewById(R.id.addManager12)
        addmanager31.setOnClickListener {
            addmanager31.startAnimation(click)
            startActivity(Intent(this@Dashboard,ManagerActivity::class.java))
        }

        AddUser = findViewById(R.id.AddUser)
        AddUser.setOnClickListener {
            AddUser.startAnimation(click)
            startActivity(Intent(this,addUser::class.java))
        }

        store = findViewById(R.id.authoStore)
        store.setOnClickListener {
            store.startAnimation(click)
            startActivity(Intent(this,AuthoraizationStrore::class.java))
        }

        dmdrqsted = findViewById(R.id.dmdrqsted)
        dmdrqsted.setOnClickListener {
            dmdrqsted.startAnimation(click)
            startActivity(Intent(this,requestedDemandPanelAdmin::class.java))
        }

        pendingdmd = findViewById(R.id.pendingdmd)
        pendingdmd.setOnClickListener {
            pendingdmd.startAnimation(click)
            startActivity(Intent(this,PendingDemandPanelAdmin::class.java))
        }

        navigationView = findViewById(R.id.navigation_view)
        drawer = findViewById(R.id.myDrawer)
        navBar = findViewById(R.id.navBar)
        navBar.setOnClickListener {
            navBar.startAnimation(click)
             drawer.openDrawer(GravityCompat.START)
        }

        val headerView = navigationView.getHeaderView(0)
        val navHeaderName: TextView = headerView.findViewById(R.id.headerName)
        val navHeaderId: TextView = headerView.findViewById(R.id.headerId)
        navHeaderName.text = adminName
        navHeaderId.text = adminEmail

        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.storereport -> {
                    // Handle the Activity 1 click
                    val intent = Intent(this, storeReport::class.java)
                    startActivity(intent)
                }
                R.id.Itp ->{
                    val intent = Intent(this,add_tech_person::class.java)
                    startActivity(intent)
                }
                R.id.Itml -> {
                    startActivity(Intent(this@Dashboard,Moderator_list::class.java))
                }
                R.id.outOfStockA ->{
                    startActivity(Intent(this,outofstock::class.java))
                }
                R.id.addOrg ->{
                    startActivity(Intent(this,addOrganization::class.java))
                }
                R.id.Org ->{
                    startActivity(Intent(this,organization_list_panel::class.java))
                }
                R.id.pendingDemandOrg -> {
                    startActivity(Intent(this,orgPendingDemands::class.java))
                }
                R.id.orgDemands -> {
                    startActivity(Intent(this,OrganizationDemandsNav::class.java))
                }
                R.id.issueReportAd->{
                    startActivity(Intent(this,Issue_report_admin::class.java))
                }

                // Add other cases for other menu items as needed
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        })
    }

    private fun profileSection(profile : ImageView,click : Animation, adminName : String ,adminEmail : String , role : String ,  id : String , department : String){
        profile.startAnimation(click)
        val it = Intent(this,profileActivity::class.java)
        it.putExtra("userName",adminName)
        it.putExtra("userEmail",adminEmail)
        it.putExtra("role",role)
        it.putExtra("id",id)
        it.putExtra("department",department)
        startActivity(it)
    }
}
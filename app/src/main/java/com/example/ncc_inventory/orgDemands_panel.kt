package com.example.ncc_inventory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class orgDemands_panel : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var backbutton : ImageView
    private lateinit var adapter: orgDemandAdapter
    private lateinit var retrofit: Retrofit
    private lateinit var nodmd : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_org_demands_panel)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        retrofit = rFit.retrofit!!
        adapter = orgDemandAdapter(this, emptyList())
        recyclerView = findViewById(R.id.orgDRecyclerViewManager)
        searchView = findViewById(R.id.orgDemandSearchView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        nodmd = findViewById(R.id.orgnodemand)
        nodmd.visibility = View.INVISIBLE
        backbutton = findViewById(R.id.orgbckaaaa)

        val service = retrofit.create(OrgDemandService::class.java)
        service.getComplanyDemands().enqueue(object : Callback<orgDemandResponse>{
            override fun onResponse(
                call: Call<orgDemandResponse>,
                response: Response<orgDemandResponse>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success==true){
                        if(respo.demands.isNotEmpty()){
                            adapter = orgDemandAdapter(this@orgDemands_panel,respo.demands)
                            recyclerView.adapter =adapter
                        }else{
                            nodmd.visibility = View.VISIBLE
                        }
                    }
                }else{
                    Toast.makeText(this@orgDemands_panel,"Response Failed",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<orgDemandResponse>, t: Throwable) {
                Toast.makeText(this@orgDemands_panel,"Some Error Occurred",Toast.LENGTH_SHORT).show()
            }

        })

        setupSearchView()

    }
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filter(newText)
                }
                return true
            }
        })
    }

}
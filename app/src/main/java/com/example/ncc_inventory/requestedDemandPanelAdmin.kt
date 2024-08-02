package com.example.ncc_inventory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class requestedDemandPanelAdmin : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView : androidx.appcompat.widget.SearchView
    private lateinit var adapter: dmdAdapter
    private lateinit var rtft : Retrofit
    private lateinit var tv : TextView
    private lateinit var backBtn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requested_demand_panel_admin)
        searchView = findViewById(R.id.serachView5)
        recyclerView = findViewById(R.id.totalrequestRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rtft = rFit.retrofit!!
        tv = findViewById(R.id.ck)
        tv.visibility = View.INVISIBLE
        backBtn = findViewById(R.id.bcka)
        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        val mylst : List<dmdRequestedItems> = listOf()
        adapter = dmdAdapter(this@requestedDemandPanelAdmin,mylst)

        getd()
        setupSearchView()

    }
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }
    private fun getd(){
        val dmdRequestService = rtft.create(dmdRequestService::class.java)
        dmdRequestService.getmyData().enqueue(object : Callback<demandrequestedResponse>{
            override fun onResponse(
                call: Call<demandrequestedResponse>,
                response: Response<demandrequestedResponse>
            ) {
              if(response.isSuccessful){
                  val respo = response.body()
                  if(respo?.success==true){
                      val lst = respo.demands
                      adapter = dmdAdapter(this@requestedDemandPanelAdmin, lst)
                      if(lst.isNotEmpty()) {
                          adapter = dmdAdapter(this@requestedDemandPanelAdmin, lst)
                          recyclerView.adapter = adapter
                      }else{
                          tv.visibility = View.VISIBLE
                      }
                  }
              }else{
                  Toast.makeText(this@requestedDemandPanelAdmin,"Response Failed",Toast.LENGTH_SHORT).show()
              }
            }

            override fun onFailure(call: Call<demandrequestedResponse>, t: Throwable) {
                Toast.makeText(this@requestedDemandPanelAdmin,"Some error occurred",Toast.LENGTH_SHORT).show()
            }
        })
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
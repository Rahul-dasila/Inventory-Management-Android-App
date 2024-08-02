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

class allproductreportpanel : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: allProductReportAdapter
    private lateinit var retrofit: Retrofit
    private lateinit var back : ImageView
    private lateinit var reportDemand : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allproductreportpanel)

        recyclerView = findViewById(R.id.reportRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        searchView = findViewById(R.id.reportSv)
        adapter = allProductReportAdapter(this, emptyList())
        retrofit = rFit.retrofit!!
        back = findViewById(R.id.reportBack)
        reportDemand = findViewById(R.id.reportdemand)
        reportDemand.visibility = View.INVISIBLE


        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        val service  = retrofit.create(AllProductReportService::class.java)
        service.getReport().enqueue(object :Callback<allProductreportresponse>{
            override fun onResponse(
                call: Call<allProductreportresponse>,
                response: Response<allProductreportresponse>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success==true){
                        if(respo.products.isNotEmpty()){
                            adapter = allProductReportAdapter(this@allproductreportpanel,respo.products)
                            recyclerView.adapter = adapter
                        }else{
                            reportDemand.visibility = View.VISIBLE
                        }
                    }
                }else{
                    Toast.makeText(this@allproductreportpanel,"Response failed",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<allProductreportresponse>, t: Throwable) {
                Toast.makeText(this@allproductreportpanel,"Some error occurred",Toast.LENGTH_SHORT).show()

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
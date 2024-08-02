package com.example.ncc_inventory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class outofstock : AppCompatActivity() {
    private lateinit var retorfit : Retrofit
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var nodea : TextView
    private lateinit var adapter : outOfStockAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outofstock)

        retorfit = rFit.retrofit!!
        recyclerView  =findViewById(R.id.oosRecyclerViewModerator)
        recyclerView.layoutManager = LinearLayoutManager(this)
        searchView = findViewById(R.id.oos_searchview)
        nodea  =findViewById(R.id.nodemandMoss)
        nodea.visibility = View.INVISIBLE
        adapter = outOfStockAdapter(this, emptyList())

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        val service  = retorfit.create(OutOfStockService::class.java)
        service.getOutofStock().enqueue(object : Callback<outofstockresponse>{
            override fun onResponse(
                call: Call<outofstockresponse>,
                response: Response<outofstockresponse>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success==true){
                     if(respo.outOfStockDemands.isNotEmpty()){
                         adapter = outOfStockAdapter(this@outofstock,respo.outOfStockDemands)
                         recyclerView.adapter = adapter
                     }else{
                         nodea.visibility = View.VISIBLE
                     }
                    }
                }else{
                    Toast.makeText(this@outofstock,"Response Failed",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<outofstockresponse>, t: Throwable) {
                Toast.makeText(this@outofstock,"Some error Occurred",Toast.LENGTH_SHORT).show()

            }

        })
        setupSearchView()
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
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }
}
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

class orgPendingDemands : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: orgPendingDEmandAdapter
    private lateinit var retrofit: Retrofit
    private lateinit var back : ImageView
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_org_pending_demands)


        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        searchView = findViewById(R.id.orgPDemandSearchView)
        recyclerView = findViewById(R.id.orgPDRecyclerViewManager)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = orgPendingDEmandAdapter(this, emptyList())
        retrofit = rFit.retrofit!!
        back = findViewById(R.id.orgPbckaaaa)
        textView = findViewById(R.id.orgnoPdemand)
        textView.visibility = View.INVISIBLE

        val service = retrofit.create(PendingDemandOrgService::class.java)
        service.pendingDemand().enqueue(object : Callback<pendingDemandResponse>{
            override fun onResponse(
                call: Call<pendingDemandResponse>,
                response: Response<pendingDemandResponse>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success==true){
                        if(respo.demands.isNotEmpty()){
                            adapter = orgPendingDEmandAdapter(this@orgPendingDemands,respo.demands)
                            recyclerView.adapter = adapter
                        }else{
                            textView.visibility = View.VISIBLE
                        }
                    }
                }else{
                    Toast.makeText(this@orgPendingDemands,"Response failed",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<pendingDemandResponse>, t: Throwable) {
                Toast.makeText(this@orgPendingDemands,"Some Error Occurred",Toast.LENGTH_SHORT).show()
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
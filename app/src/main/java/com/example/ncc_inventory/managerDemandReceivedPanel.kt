package com.example.ncc_inventory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class managerDemandReceivedPanel : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView : androidx.appcompat.widget.SearchView
    private lateinit var adapter: managerDemandReceivedAdapter
    private lateinit var rtft : Retrofit
    private lateinit var tv : TextView
    private lateinit var backBtn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_demand_received_panel)
        searchView = findViewById(R.id.managerDemandSearchView)
        recyclerView = findViewById(R.id.totalrequestRecyclerViewManager)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rtft = rFit.retrofit!!
        tv = findViewById(R.id.nodemand)
        tv.visibility = View.INVISIBLE
        backBtn = findViewById(R.id.bckaaaa)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        val mylst : List<dmdRequestedItems> = listOf()
        adapter = managerDemandReceivedAdapter(this@managerDemandReceivedPanel,mylst)


        getd()
        setupSearchView()

    }

    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }
    private fun getd(){
        val dmdRequestService = rtft.create(dmdRequestService::class.java)
        dmdRequestService.getmyData().enqueue(object : Callback<demandrequestedResponse> {
            override fun onResponse(
                call: Call<demandrequestedResponse>,
                response: Response<demandrequestedResponse>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success==true){
                        val lst = respo.demands
                        adapter = managerDemandReceivedAdapter(this@managerDemandReceivedPanel, lst)
                        if(lst.isNotEmpty()) {
                            adapter = managerDemandReceivedAdapter(this@managerDemandReceivedPanel, lst)
                            recyclerView.adapter = adapter
                        }else{
                            tv.visibility = View.VISIBLE
                        }
                    }
                }else{
                    Toast.makeText(this@managerDemandReceivedPanel,"Response Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<demandrequestedResponse>, t: Throwable) {
                Toast.makeText(this@managerDemandReceivedPanel,"Some error occurred", Toast.LENGTH_SHORT).show()
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
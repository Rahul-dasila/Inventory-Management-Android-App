package com.example.ncc_inventory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PendingDemandPanelAdmin : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView : androidx.appcompat.widget.SearchView
    private lateinit var adapter: dmdAdapter
    private lateinit var rtft : Retrofit
    private lateinit var tv : TextView
    private lateinit var backBtn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_demand_panel_admin)

        val mylst : List<dmdRequestedItems> = listOf()
        adapter = dmdAdapter(this@PendingDemandPanelAdmin,mylst)
        recyclerView = findViewById(R.id.pendingRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        searchView = findViewById(R.id.serachView6)
         rtft = rFit.retrofit!!
        tv = findViewById(R.id.dk)
        tv.visibility = View.INVISIBLE
        backBtn = findViewById(R.id.pendingBack)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

      getData()
      setupSearchView()
    }
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }

    private fun getData() {
        val service  = rtft.create(PendingDemandService::class.java)
        service.getPending().enqueue(object : Callback<demandrequestedResponse>{
            override fun onResponse(
                call: Call<demandrequestedResponse>,
                response: Response<demandrequestedResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PendingDemandPanelAdmin,"f", Toast.LENGTH_SHORT).show()
                    val respo = response.body()
                    if (respo?.success == true) {
                        Toast.makeText(this@PendingDemandPanelAdmin,"k", Toast.LENGTH_SHORT).show()
                        val lst = respo.demands
                        adapter = dmdAdapter(this@PendingDemandPanelAdmin, lst)
                        if (lst.isNotEmpty()) {
                            adapter = dmdAdapter(this@PendingDemandPanelAdmin, lst)
                            recyclerView.adapter = adapter
                        } else {
                            tv.visibility = View.VISIBLE
                        }
                    }
                }
                else{
                    Toast.makeText(this@PendingDemandPanelAdmin,"Response Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<demandrequestedResponse>, t: Throwable) {
                Toast.makeText(this@PendingDemandPanelAdmin,"Some Error occurred", Toast.LENGTH_SHORT).show()
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
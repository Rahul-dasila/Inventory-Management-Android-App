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

class issue_product_moderator_dashboard : AppCompatActivity() {
    private lateinit var searchView : androidx.appcompat.widget.SearchView
    private lateinit var adapter : issue_demand_adapter
    private lateinit var retrofit: Retrofit
    private lateinit var backBtn : ImageView
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_product_moderator_dashboard)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        val tv = findViewById<TextView>(R.id.nodemandMo)
        tv.visibility = View.INVISIBLE

        searchView = findViewById(R.id.isp_searchview)
        recyclerView = findViewById(R.id.totalrequestRecyclerViewModerator)
        retrofit = rFit.retrofit!!
        adapter = issue_demand_adapter(this@issue_product_moderator_dashboard, emptyList(),retrofit,tv)
        backBtn = findViewById(R.id.bckaaaaM)
        var service = retrofit.create(issueDemandService::class.java)
        service.issueDemands().enqueue(object : Callback<issuedemandresponse>{
            override fun onResponse(
                call: Call<issuedemandresponse>,
                response: Response<issuedemandresponse>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success == true){
                        adapter = issue_demand_adapter(this@issue_product_moderator_dashboard,respo.demands,retrofit,tv)
                        recyclerView.layoutManager = LinearLayoutManager(this@issue_product_moderator_dashboard)
                        recyclerView.adapter = adapter
                    }

                    if(respo?.demands.isNullOrEmpty()){
                        tv.visibility = View.VISIBLE
                    }
                }else{
                    Toast.makeText(this@issue_product_moderator_dashboard,"Response Unsuccessful",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<issuedemandresponse>, t: Throwable) {
                Toast.makeText(this@issue_product_moderator_dashboard,"Some error occurred",Toast.LENGTH_SHORT).show()

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
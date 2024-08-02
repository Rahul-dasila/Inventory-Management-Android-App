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

class issueOrg : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: issueOrgAdapter
    private lateinit var retrofit: Retrofit
    private lateinit var back : ImageView
    private lateinit var tv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_org)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        back = findViewById(R.id.Iback)
        searchView = findViewById(R.id.Isearchview)
        recyclerView = findViewById(R.id.IRecyclerViewModerator)
        recyclerView.layoutManager = LinearLayoutManager(this)
        retrofit = rFit.retrofit!!
        adapter = issueOrgAdapter(this, emptyList())
        tv = findViewById(R.id.InodemandMo)
        tv.visibility = View.INVISIBLE

        val service = retrofit.create(issueDemandService::class.java)
        service.issueDemandsOrg().enqueue(object : Callback<issueDemandResponseOrg>{
            override fun onResponse(
                call: Call<issueDemandResponseOrg>,
                response: Response<issueDemandResponseOrg>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success == true){
                        if(respo.demands.isNotEmpty()) {
                            adapter = issueOrgAdapter(this@issueOrg, respo.demands)
                            recyclerView.layoutManager = LinearLayoutManager(this@issueOrg)
                            recyclerView.adapter = adapter
                        }else{
                            tv.visibility = View.INVISIBLE
                        }
                    }
                }else{
                    Toast.makeText(this@issueOrg,"Response Unsuccessful",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<issueDemandResponseOrg>, t: Throwable) {
                Toast.makeText(this@issueOrg,"Some Error Occurred",
                    Toast.LENGTH_SHORT).show()
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
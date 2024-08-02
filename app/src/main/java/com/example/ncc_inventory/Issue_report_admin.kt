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

class Issue_report_admin : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Issued_ProductsAdapter
    private lateinit var retrofit: Retrofit
    private lateinit var back : ImageView
    private lateinit var reportDemand : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_report_admin)


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        recyclerView = findViewById(R.id.IssuereportRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        searchView = findViewById(R.id.IssuereportSv)
        adapter = Issued_ProductsAdapter(this, emptyList())
        retrofit = rFit.retrofit!!
        back = findViewById(R.id.issuereportBack)
        reportDemand = findViewById(R.id.Issuereportdemand)
        reportDemand.visibility = View.INVISIBLE

        val service  = retrofit.create(issuedProductService::class.java)
        service.getProducts().enqueue(object : Callback<issue_Product_report_res> {
            override fun onResponse(
                call: Call<issue_Product_report_res>,
                response: Response<issue_Product_report_res>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success==true){
                        if(respo.issueList.isNotEmpty()){
                            adapter = Issued_ProductsAdapter(this@Issue_report_admin,respo.issueList)
                            recyclerView.adapter = adapter
                        }else{
                            reportDemand.visibility = View.VISIBLE
                        }
                    }
                }else{
                    Toast.makeText(this@Issue_report_admin,"Response failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<issue_Product_report_res>, t: Throwable) {
                Toast.makeText(this@Issue_report_admin,"Some error occurred", Toast.LENGTH_SHORT).show()

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
package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class organization_list_panel : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: orgListAdapter
    private lateinit var back: ImageView
    private lateinit var retrofit: Retrofit
    private val REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organization_list_panel)


        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        searchView = findViewById(R.id.orgListSerachView)
        recyclerView = findViewById(R.id.orglistRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = orgListAdapter(this, emptyList())

        retrofit = rFit.retrofit!!
        val service = retrofit.create(orgListService::class.java)
        service.getOrglist().enqueue(object : Callback<orglistRes>{
            override fun onResponse(call: Call<orglistRes>, response: Response<orglistRes>) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success==true){
                        if(respo.companies.isNotEmpty()){
                            adapter = orgListAdapter(this@organization_list_panel,respo.companies)
                            recyclerView.adapter = adapter
                        }
                    }else{
                        Toast.makeText(this@organization_list_panel,"Response Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<orglistRes>, t: Throwable) {
                Toast.makeText(this@organization_list_panel,"Response Failed",Toast.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Finish the current activity to remove it from the stack
            finish()
        }
    }

    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }


}
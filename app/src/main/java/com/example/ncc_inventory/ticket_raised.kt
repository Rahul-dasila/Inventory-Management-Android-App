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

class ticket_raised : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var back : ImageView
    private lateinit var adapter: ticketRaisedAdapter
    private lateinit var retrofit: Retrofit
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_raised)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        searchView = findViewById(R.id.svRT)
        recyclerView = findViewById(R.id.rvRT)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ticketRaisedAdapter(this, emptyList())
        retrofit = rFit.retrofit!!
        back = findViewById(R.id.bcRT)
        textView = findViewById(R.id.ogtc)
        textView.visibility = View.INVISIBLE

        val service  = retrofit.create(raisedServiceMode::class.java)
        service.getTickets().enqueue(object : Callback<raisedReesponse>{
            override fun onResponse(
                call: Call<raisedReesponse>,
                response: Response<raisedReesponse>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success==true){
                        if(respo.tickets.isNotEmpty()){
                            adapter = ticketRaisedAdapter(this@ticket_raised,respo.tickets)
                            recyclerView.adapter = adapter
                        }else{
                            textView.visibility = View.INVISIBLE
                        }
                    }
                }else{
                    Toast.makeText(this@ticket_raised,"Response Failed",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<raisedReesponse>, t: Throwable) {
                Toast.makeText(this@ticket_raised,"Some error occurred",Toast.LENGTH_SHORT).show()
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
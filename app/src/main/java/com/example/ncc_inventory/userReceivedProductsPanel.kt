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

class userReceivedProductsPanel : AppCompatActivity() {
    private lateinit var searchView : SearchView
    private lateinit var adapter: receivedDemandAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var retrofit: Retrofit
    private lateinit var backButton : ImageView
    private lateinit var rk : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_received_products_panel)


        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        //ddd
        searchView = findViewById(R.id.serachView9)
        recyclerView = findViewById(R.id.receivedRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = receivedDemandAdapter(this, emptyList(),"")
        retrofit = rFit.retrofit!!
        backButton = findViewById(R.id.receivedBack)
        rk = findViewById(R.id.rk)
        rk.visibility = View.INVISIBLE


        val id = intent.getStringExtra("id")


        val receivedUserReq = id?.let { receivedUserReq(it) }
        val service = retrofit.create(receivedUserService::class.java)
        if (receivedUserReq != null) {

            service.receivedProducts(receivedUserReq).enqueue(object : Callback<receivedUserRes>{
                override fun onResponse(
                    call: Call<receivedUserRes>,
                    response: Response<receivedUserRes>
                ) {

                    if(response.isSuccessful){
                        val respo = response.body()
                        if(respo?.success==true){
                            if(respo.products.isNotEmpty()){
                                adapter = receivedDemandAdapter(this@userReceivedProductsPanel,respo.products,id)
                                recyclerView.adapter = adapter
                            }else{
                                rk.visibility = View.VISIBLE
                            }
                        }else{
                            Toast.makeText(this@userReceivedProductsPanel,"Response Failed",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<receivedUserRes>, t: Throwable) {
                    Toast.makeText(this@userReceivedProductsPanel,"Some error occurred",Toast.LENGTH_SHORT).show()
                }

            })
        }
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
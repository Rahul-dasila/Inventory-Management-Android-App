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

class Moderator_list : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: moderatorAdapter
    private lateinit var back: ImageView
    private lateinit var retrofit: Retrofit
    private val REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moderator_list)

        searchView = findViewById(R.id.moderatorListSerachView)
        recyclerView = findViewById(R.id.moderatorlistRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = moderatorAdapter(this, emptyList())

        back = findViewById(R.id.mbbb)

        //For transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }


        retrofit = rFit.retrofit!!
        val service = retrofit.create(ModeratorListService::class.java)
        service.getModeratorList().enqueue(object : Callback<List<moderators>>{
            override fun onResponse(
                call: Call<List<moderators>>,
                response: Response<List<moderators>>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(!respo.isNullOrEmpty()){
                        adapter = moderatorAdapter(this@Moderator_list,respo)
                        recyclerView.adapter = adapter
                    }
                }else{
                    Toast.makeText(this@Moderator_list,"Response unsuccessful",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<moderators>>, t: Throwable) {
                Toast.makeText(this@Moderator_list,"Some Error Occurred",Toast.LENGTH_SHORT).show()
            }

        })


        setupSearchView()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
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
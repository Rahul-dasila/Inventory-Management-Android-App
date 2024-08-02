package com.example.ncc_inventory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
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
import retrofit2.create
import java.sql.Time

class checkDemandStatusUser : AppCompatActivity() {
    private lateinit var searchView : androidx.appcompat.widget.SearchView
    private lateinit var RecyclerView : RecyclerView
    private lateinit var adapter: statusDemandAdapter
    private lateinit var rft : Retrofit
    private lateinit var nodmd : TextView
    private lateinit var bck : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_demand_status_user)
        RecyclerView = findViewById(R.id.requestRecyclerView)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        rft = rFit.retrofit!!
        nodmd = findViewById(R.id.nodmd)
        nodmd.visibility= View.INVISIBLE
        searchView = findViewById(R.id.serachView4)
        val userId = intent.getStringExtra("id").toString()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        val statusRequestUser = statusRequestUser(userId)
        val checkStatusServiceUser = rft.create(checkStatusServiceUser::class.java)
        checkStatusServiceUser.getStatus(statusRequestUser).enqueue(object : Callback<statusResponseUser>{
            override fun onResponse(
                call: Call<statusResponseUser>,
                response: Response<statusResponseUser>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success == true){
                        if(!respo.data.isNullOrEmpty()) {
                            var mL: List<requestStatus> = respo.data
                            adapter = statusDemandAdapter(mL)
                            RecyclerView.adapter = adapter
                        }else{
                            nodmd.visibility = View.VISIBLE
                        }
                    }
                }
                else{
                    Toast.makeText(this@checkDemandStatusUser,"Response Failed ",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<statusResponseUser>, t: Throwable) {
                Toast.makeText(this@checkDemandStatusUser,"Some error Occurred",Toast.LENGTH_SHORT).show()
            }
        })
        setupSearchView()

        val click = AnimationUtils.loadAnimation(this,R.anim.click)
        bck = findViewById(R.id.bck)
        bck.setOnClickListener {
            bck.startAnimation(click)
            // Check if there is a parent activity specified
            val parentIntent = NavUtils.getParentActivityIntent(this)
            if (parentIntent != null) {
                // Navigate to the parent activity
                NavUtils.navigateUpTo(this, parentIntent)
            } else {
                // Handle the case where no parent activity is specified (optional)
                finish()
            }
        }
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
package com.example.ncc_inventory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class filterUserAssign : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: filterItem_adapter
    private lateinit var back: ImageView
    private lateinit var retrofit: Retrofit
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_user_assign)


        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }



        retrofit = rFit.retrofit!!

        textView = findViewById(R.id.nodemandfil)

        val productType = intent.getStringExtra("type")
        val productModel = intent.getStringExtra("model")
        val productBrand = intent.getStringExtra("brand")
        val userId = intent.getStringExtra("userId")
        val demandId = intent.getStringExtra("demandId")
        val quantity = intent.getIntExtra("quantity",0)

        back = findViewById(R.id.filterBack)
        back.setOnClickListener {
            onBackPressed()
        }
        recyclerview = findViewById(R.id.filterItemRV)
        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = filterItem_adapter(this, emptyList(), retrofit,demandId!!,userId!!
        ){
            vb()
        }

        val filterProductRequest =
            productBrand?.let {
                productType?.let { it1 ->
                    productModel?.let { it2 ->
                        filterProductRequest(
                            it1,
                            it2, it,quantity
                        )
                    }
                }
            }

        val service = retrofit.create(filterproductService::class.java)
        if (filterProductRequest != null) {
            service.getItems(filterProductRequest).enqueue(object : Callback<fResponse> {
                override fun onResponse(call: Call<fResponse>, response: Response<fResponse>) {
                    if (response.isSuccessful) {
                        val respo = response.body()
                        if (respo?.success == true) {
                            var newList = respo.filteredProducts
                            if (newList != null) {
                                adapter = filterItem_adapter(
                                    this@filterUserAssign,
                                    newList,
                                    retrofit,
                                    demandId,
                                    userId
                                ) {
                                    vb()
                                }
                                recyclerview.adapter = adapter
                            }
                        }
                        if(respo?.filteredProducts.isNullOrEmpty()){
                            textView.visibility = View.VISIBLE
                        }else{
                            textView.visibility = View.INVISIBLE
                        }
                    } else {
                        Toast.makeText(
                            this@filterUserAssign,
                            "Response unsuccessful",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<fResponse>, t: Throwable) {
                    Toast.makeText(
                        this@filterUserAssign,
                        "Response unsuccessful",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
        textView.visibility = View.VISIBLE
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val intent = Intent(this@filterUserAssign,issue_product_moderator_dashboard::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
    fun vb(){
        textView.visibility = View.VISIBLE
    }


}

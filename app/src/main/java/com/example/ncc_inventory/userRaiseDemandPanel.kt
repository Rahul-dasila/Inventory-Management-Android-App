package com.example.ncc_inventory

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.location.LocationRequestCompat.Quality
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class userRaiseDemandPanel : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<String>
    private val items = mutableListOf("PC","Desktop","Mouse","Keyboard","Add Item")
    private lateinit var selectedItem2 : String
    private lateinit var productName : String
    private lateinit var pBrand :String
    private lateinit var pQuantity : String
    private lateinit var pModel : String
    private lateinit var aDetails : String
    private lateinit var raiseDemandbtn  : TextView
    private lateinit var uName : EditText
    private lateinit var uBrand:EditText
    private lateinit var uQuantity : EditText
    private lateinit var uModel : EditText
    private lateinit var uD : EditText
    private lateinit var click :Animation
    private lateinit var userId : String
    private lateinit var userDesig : String
    private lateinit var retrofiit : Retrofit
    private lateinit var checkStatus : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_raise_demand_panel)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


         selectedItem2 = ""
        //Spinner for product type field in ADD category section
        spinner = findViewById(R.id.userspinner)
        adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == items.size - 1) { // "Add Item" selected
                    showAddItemDialog()
                }
                selectedItem2 = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        })

        retrofiit = rFit.retrofit!!

        checkStatus = findViewById(R.id.dmStatus)
        uName = findViewById(R.id.userproductName)
        uBrand = findViewById(R.id.userbrand)
        uQuantity = findViewById(R.id.quantity)
        uModel = findViewById(R.id.userProductmodel)
        uD = findViewById(R.id.useradditionalDetails)
        raiseDemandbtn = findViewById(R.id.raise)
        productName = ""
        pBrand = ""
        pQuantity = ""
        pModel = ""
        aDetails = ""
        userId = ""
        userDesig= ""

        click = AnimationUtils.loadAnimation(this,R.anim.click)
        raiseDemandbtn.setOnClickListener {
            raiseDemandbtn.startAnimation(click)
            productName = uName.text.toString()
            pBrand = uBrand.text.toString()
            pQuantity = uQuantity.text.toString()
            pModel = uModel.text.toString()
            aDetails = uD.text.toString()
            if(pModel.isNullOrEmpty()){
                pModel = "None"
            }
            if(aDetails.isNullOrEmpty()){
                aDetails = "None"
            }
            if(!productName.isNullOrEmpty() && !pBrand.isNullOrEmpty() && !pQuantity.isNullOrEmpty()){
                sendDemand()
            }else{
                Toast.makeText(this,"Please Fill the mandatory fields",Toast.LENGTH_SHORT).show()
            }
        }
        checkStatus.setOnClickListener {
            checkStatus.startAnimation(click)
            userId = intent.getStringExtra("id").toString()
            val it = Intent(this,checkDemandStatusUser::class.java)
            it.putExtra("id",userId)
            startActivity(it)
        }

    }

    private fun sendDemand() {
     userId = intent.getStringExtra("id").toString()
     userDesig = intent.getStringExtra("desig").toString()
     val demandID = generateId()
     val demand = demand(demandID,userId,userDesig,selectedItem2,productName,pModel,pBrand,aDetails,pQuantity.toInt())
     val service = retrofiit.create(UserRaiseDemandService::class.java)
     service.raiseDemand(demand).enqueue(object : Callback<userRaiseDemandResponse>{
         override fun onResponse(
             call: Call<userRaiseDemandResponse>,
             response: Response<userRaiseDemandResponse>
         ) {
             if(response.isSuccessful){
                val respo = response.body()
                 if(respo?.success==true){
                     Toast.makeText(this@userRaiseDemandPanel,"Successfully demand raised",Toast.LENGTH_SHORT).show()
                     uName.text.clear()
                     uModel.text.clear()
                     uBrand.text.clear()
                     uQuantity.text.clear()
                     uD.text.clear()
                 }
             }
             else{
                 Toast.makeText(this@userRaiseDemandPanel,"Some error occurred",Toast.LENGTH_SHORT).show()
             }
         }

         override fun onFailure(call: Call<userRaiseDemandResponse>, t: Throwable) {
             Toast.makeText(this@userRaiseDemandPanel,"failed response",Toast.LENGTH_SHORT).show()
         }
     })


    }


    private fun showAddItemDialog() {
        val builder = AlertDialog.Builder(this@userRaiseDemandPanel)
        val inflater = LayoutInflater.from(this@userRaiseDemandPanel)
        val dialogView: View = inflater.inflate(R.layout.additemdialog, null)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.show()

        val editTextNewItem = dialogView.findViewById<EditText>(R.id.dAdditem)
        val addBtn = dialogView.findViewById<TextView>(R.id.dAddBtn)
        val cancelBtn = dialogView.findViewById<TextView>(R.id.dCancelBtn)

        addBtn.setOnClickListener {
            val newItem = editTextNewItem.text.toString()
            if (newItem.isNotEmpty()) {
                if(!vald(newItem)) {
                    items.add(items.size - 1, newItem) // Add before "Add Item"
                    adapter.notifyDataSetChanged()
                    selectedItem2 = newItem
                }
                else{
                    Toast.makeText(this,"Item Already exists in list",Toast.LENGTH_SHORT).show()
                }

            }
            dialog.dismiss()
        }
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun vald(item :String) : Boolean{
        var ck = false
        for (i  in  items.indices){
            if(item.lowercase().trim() == items[i].lowercase().trim()){
                ck = true
                break
            }
        }
        return ck
    }
    private fun generateId():String{
        val firstTwo = kotlin.random.Random.nextInt(10,99).toString()
        val middle1 = kotlin.random.Random.nextInt('A'.code,'Z'.code+1).toChar()
        val middle2 = kotlin.random.Random.nextInt('A'.code,'Z'.code+1).toChar()
        val middle3 = kotlin.random.Random.nextInt('A'.code,'Z'.code+1).toChar()
        val lastTwo = kotlin.random.Random.nextInt(10,99).toString()
        val two = kotlin.random.Random.nextInt(10,99).toString()
        val middle4 = kotlin.random.Random.nextInt('A'.code,'Z'.code+1).toChar()
        val ID = firstTwo+middle1+middle2+two+middle3+middle4+lastTwo
        return ID
    }
}
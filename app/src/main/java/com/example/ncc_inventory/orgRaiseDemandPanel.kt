package com.example.ncc_inventory

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
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class orgRaiseDemandPanel : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<String>
    private val items = mutableListOf("PC","Desktop","Mouse","Keyboard","Add Item")
    private lateinit var selectedItem3 : String
    private lateinit var productName : String
    private lateinit var pBrand :String
    private lateinit var pQuantity : String
    private lateinit var pModel : String
    private lateinit var aDetails : String
    private lateinit var raiseDemandbtn  : TextView
    private lateinit var oName : EditText
    private lateinit var oBrand:EditText
    private lateinit var oQuantity : EditText
    private lateinit var oModel : EditText
    private lateinit var oD : EditText
    private lateinit var click : Animation
    private lateinit var retrofit: Retrofit
    private lateinit var bb : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_org_raise_demand_panel)


        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        bb = findViewById(R.id.obackButton)
        retrofit = rFit.retrofit!!
        selectedItem3 = ""
        spinner = findViewById(R.id.orgspinner)
        adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == items.size - 1) { // "Add Item" selected
                    showAddItemDialog()
                }
                selectedItem3 = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        })

        oName = findViewById(R.id.oproductName)
        oBrand = findViewById(R.id.obrand)
        oQuantity = findViewById(R.id.oquantity)
        oModel = findViewById(R.id.oProductmodel)
        oD = findViewById(R.id.oadditionalDetails)
        raiseDemandbtn = findViewById(R.id.oraise)
        productName = ""
        pBrand = ""
        pQuantity = ""
        pModel = ""
        aDetails = ""

        click = AnimationUtils.loadAnimation(this,R.anim.click)
        raiseDemandbtn.setOnClickListener {
            raiseDemandbtn.startAnimation(click)
            raiseDemandbtn.startAnimation(click)
            productName = oName.text.toString()
            pBrand = oBrand.text.toString()
            pQuantity = oQuantity.text.toString()
            pModel = oModel.text.toString()
            aDetails = oD.text.toString()
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

    }

    private fun sendDemand(){
        val id = intent.getStringExtra("id")
        val orgRaiseDemandRequest =
            id?.let { orgRaiseDemandRequest(generateId(), it,selectedItem3,productName,pModel,pBrand,aDetails,pQuantity.trim().toInt()) }
        val service  = retrofit.create(OrgRaiseDemandService::class.java)
        if (orgRaiseDemandRequest != null) {
            service.raiseDemnd(orgRaiseDemandRequest).enqueue(object : Callback<orgRaiseDemandResponse>{
                override fun onResponse(
                    call: Call<orgRaiseDemandResponse>,
                    response: Response<orgRaiseDemandResponse>
                ) {
                    if(response.isSuccessful){
                        val respo = response.body()
                        if(respo?.success==true){
                            Toast.makeText(this@orgRaiseDemandPanel,"Successfully demand raised",Toast.LENGTH_SHORT).show()
                            oName.text.clear()
                            oModel.text.clear()
                            oBrand.text.clear()
                            oQuantity.text.clear()
                            oD.text.clear()
                        }
                    }
                    else{
                        Toast.makeText(this@orgRaiseDemandPanel,"Response Failed",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<orgRaiseDemandResponse>, t: Throwable) {
                    Toast.makeText(this@orgRaiseDemandPanel,"Some error occurred",Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    private fun showAddItemDialog() {
        val builder = AlertDialog.Builder(this@orgRaiseDemandPanel)
        val inflater = LayoutInflater.from(this@orgRaiseDemandPanel)
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
                    selectedItem3 = newItem
                }
                else{
                    Toast.makeText(this,"Item Already exists in list", Toast.LENGTH_SHORT).show()
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
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }

}
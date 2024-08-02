package com.example.ncc_inventory

import android.app.Activity
import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.provider.Settings
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Multipart
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.security.Permission
import java.util.Random

class addCategory : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE = 100
        private const val REQUEST_CODE_PERMISSION_MANAGE_EXTERNAL_STORAGE = 101
    }

    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<String>
    private val items = mutableListOf("PC","Desktop","Mouse","Keyboard","Add Item")
    private lateinit var selectedItem : String
    private lateinit var generateBtn : TextView
    private lateinit var mainId : TextView
    private lateinit var id : String
    private lateinit var productName : EditText
    private lateinit var productBrand : EditText
    private lateinit var productPrice : EditText
    private lateinit var productModel : EditText
    private lateinit var additionalDetails : EditText
    private lateinit var addbtn : TextView
    private lateinit var name :String
    private lateinit var brand : String
    private lateinit var price : String
    private lateinit var dt : String
    private lateinit var model : String
    private lateinit var productService: ProductService
    private lateinit var backButton :ImageView
    private lateinit var bulkUpload : TextView
    private lateinit var retrofit : Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)


        //
        retrofit = rFit.retrofit!!


        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        //Setting click Animation
        val click = AnimationUtils.loadAnimation(this,R.anim.click)

        //Spinner for product type field in ADD category section
        spinner = findViewById(R.id.spinner)
        adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == items.size - 1) { // "Add Item" selected
                    showAddItemDialog()
                }
                selectedItem = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        })


        //ID generation for product
        id=""
        generateBtn = findViewById(R.id.generateBtn)
        mainId = findViewById(R.id.mainId)
        generateBtn.setOnClickListener {
            generateBtn.startAnimation(click)
            mainId.text = generateId()
            id = mainId.text.toString()
        }


        //getting product name , Product Brand , product price , product model and additional details
        productName = findViewById(R.id.productName)
        productBrand = findViewById(R.id.brand)
        productPrice = findViewById(R.id.price)
        productModel = findViewById(R.id.model)
        additionalDetails = findViewById(R.id.additionalDetails)

        //setting add button
        addbtn = findViewById(R.id.addBtn)

        addbtn.setOnClickListener {
            addbtn.startAnimation(click)
            name = productName.text.toString()
            brand = productBrand.text.toString()
            price = productPrice.text.toString()
            model = productModel.text.toString()
            dt = additionalDetails.text.toString()
            if(model.isEmpty()){
                model = "None"
            }
            if(dt.isEmpty()){
                dt = "None"
            }
            if(validation(id,selectedItem,name,brand,price)){
             val product = Product(selectedItem,id,name,brand,price,model,dt)
             addProduct(product)
             productName.text.clear()
             productBrand.text.clear()
             productPrice.text.clear()
             productModel.text.clear()
             additionalDetails.text.clear()
             mainId.text=""
             id = ""
            }
            else{
                Toast.makeText(this,"Please fill all the mandatory fields properly",Toast.LENGTH_SHORT).show()
            }
        }

        //back button initialization
        backButton = findViewById(R.id.backButton)



        bulkUpload = findViewById(R.id.bulkUpload)
        bulkUpload.setOnClickListener {
            bulkUpload.startAnimation(click)
            val builder = AlertDialog.Builder(this@addCategory)
            val inflater = LayoutInflater.from(this@addCategory)
            val dialogView: View = inflater.inflate(R.layout.showalert, null)
            builder.setView(dialogView)

            val dialog = builder.create()
            dialog.show()
            val btn: TextView = dialogView.findViewById(R.id.gotIt)
            btn.setOnClickListener {
                //Checking Permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (!Environment.isExternalStorageManager()) {
                        requestManageExternalStoragePermission()
                    } else {
                        openFilePicker()
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestReadExternalStoragePermission()
                    } else {
                        openFilePicker()
                    }
                } else {
                    openFilePicker()
                }
                dialog.dismiss()
            }
        }

    }
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }


    private fun addProduct(product : Product){
        productService = retrofit.create(ProductService::class.java)
        productService.addProduct(product).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    if (productResponse?.success == true) {
                        Toast.makeText(this@addCategory, "Product added successfully!", Toast.LENGTH_SHORT).show()
                        // Handle successful product addition (optional)
                    } else {
                        Toast.makeText(this@addCategory, productResponse?.message ?: "Product addition failed", LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@addCategory, "Failed to add product: ${response.code()}", LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(this@addCategory, "Error: ${t.message}", LENGTH_SHORT).show()
            }
        })
    }

    private fun requestReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE
        )
    }

    private fun requestManageExternalStoragePermission() {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = Uri.parse("package:$packageName")
            startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_EXTERNAL_STORAGE)
        } catch (e: Exception) {
            Toast.makeText(this, "Permission request failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFilePicker()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PERMISSION_MANAGE_EXTERNAL_STORAGE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                openFilePicker()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                // Check if selected file is a CSV
                if (isCsvFile(uri)) {
                    readCSVFromUri(uri)
                } else {
                    Toast.makeText(this, "Please select a CSV file", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*" // Allow picking any type of file
        }
        startActivityForResult(intent, REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE)
    }
    private fun readCSVFromUri(uri: Uri) {
        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    val requiredHeaders = listOf(
                        "productId", "productType", "productName", "productModel",
                        "productBrand", "productPrice", "additionalDetail"
                    )

                    // Read the first line for headers
                    val headerLine = reader.readLine() ?: throw IOException("Empty CSV file")
                    val headers = headerLine.split(",").map { it.trim() }

                    // Check if all required headers are present
                    if (!headers.containsAll(requiredHeaders)) {
                        Toast.makeText(this, "CSV file missing required headers", Toast.LENGTH_LONG).show()
                        return
                    }

                    // Check header indices before accessing the values
                    val headerIndices = requiredHeaders.map { header ->
                        val index = headers.indexOf(header)
                        if (index == -1) {
                            Toast.makeText(this, "CSV file missing required header: $header", Toast.LENGTH_LONG).show()
                            return
                        }
                        index
                    }

                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        val values = line!!.split(",").map { it.trim() }

                        // Ensure the values list has enough elements
                        if (values.size < headers.size) {
                            Toast.makeText(this, "CSV line has fewer values than headers", Toast.LENGTH_SHORT).show()
                            continue
                        }

                        // Assuming the order of headers is fixed and known
                        val productType = values[headerIndices[1]]
                        val productId = values[headerIndices[0]]
                        val productName = values[headerIndices[2]]
                        val productBrand = values[headerIndices[4]]
                        val productPrice = values[headerIndices[5]]
                        var productModel = values[headerIndices[3]]
                        var additionalDetails = values[headerIndices[6]]
                        if(productModel==null || productModel.isEmpty()|| productModel==""){
                            productModel = "None"
                        }
                        if(additionalDetails == null || productModel.isEmpty()||additionalDetails==""){
                            additionalDetails = "None"
                        }
                        if(validation(productId,productType,productName,productBrand,productPrice)){
                        val prd = Product(productType,productId,productName,productBrand,productPrice,productModel,additionalDetails)
                        addProduct(prd)
                        }
                        else{
                          Toast.makeText(this@addCategory,"Mandatory field error in Product no:$line",Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error reading CSV file", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "An error occurred while processing the CSV file", Toast.LENGTH_LONG).show()
        }
    }
    private fun isCsvFile(uri: Uri): Boolean {
        val mimeType = contentResolver.getType(uri)
        return mimeType?.let {
            it.equals("text/csv", ignoreCase = true) || it.equals("text/comma-separated-values", ignoreCase = true)
        } ?: false
    }
    private fun showAddItemDialog() {
        val builder = AlertDialog.Builder(this@addCategory)
        val inflater = LayoutInflater.from(this@addCategory)
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
                items.add(items.size - 1, newItem) // Add before "Add Item"
                adapter.notifyDataSetChanged()
                selectedItem = newItem

            }
            dialog.dismiss()
        }
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
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
    private fun validation(id:String,selectedItem:String,name:String,brand:String,price:String):Boolean{
        var sk = false
        if(id.isNotEmpty() && id != ""){
            if (selectedItem.isNotEmpty()){
                if(name.isNotEmpty()){
                    if(brand.isNotEmpty()){
                        if(price.isNotEmpty()){
                            sk = true
                        }
                    }
                }
            }
        }
        return sk
    }
}
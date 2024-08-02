package com.example.ncc_inventory

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NavUtils
import ir.mahozad.android.PieChart
import ir.mahozad.android.component.Alignment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create


class AuthoraizationStrore : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var getDetailsBtn: TextView
    private lateinit var myAdapter: ArrayAdapter<String>
    private var items = mutableListOf("mk", "Desktop", "Mouse", "Keyboard", "Add Item")
    private lateinit var rtfit: Retrofit
    private lateinit var myproductList: MutableList<String>
    private lateinit var click: Animation
    private lateinit var myItem: String
    private var totalProducts: String = ""
    private var HELD = ""
    private var ISSUED = ""
    private var SERVICEABLE = ""
    private var UNSERVICEABLE = ""
    private var BER = ""
    private lateinit var pieChart: PieChart
    private lateinit var pieChart2: PieChart
    private lateinit var pieChart3: PieChart
    private lateinit var fLayout: FrameLayout
    private lateinit var linearLayout1: LinearLayout
    private lateinit var linearLayout2: LinearLayout
    private lateinit var issueBtn: TextView
    private lateinit var held: TextView
    private lateinit var sB: TextView
    private lateinit var uSB: TextView
    private lateinit var ber: TextView
    private lateinit var myTextView: TextView
    private lateinit var back : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authoraization_strore)

        //For transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        fLayout = findViewById(R.id.fL)
        fLayout.visibility = View.INVISIBLE

        linearLayout1 = findViewById(R.id.l1)
        linearLayout1.visibility = View.INVISIBLE

        linearLayout2 = findViewById(R.id.l2)
        linearLayout2.visibility = View.INVISIBLE

        pieChart = findViewById(R.id.pieChart)
        pieChart2 = findViewById(R.id.pieChart2)
        pieChart3 = findViewById(R.id.pieChart3)

        issueBtn = findViewById(R.id.issueBtn)
        held = findViewById(R.id.heldBtn)
        sB = findViewById(R.id.Sbtn)
        uSB = findViewById(R.id.uBtn)
        ber = findViewById(R.id.berBtn)

        issueBtn.visibility = View.INVISIBLE
        held.visibility = View.INVISIBLE
        sB.visibility = View.INVISIBLE
        uSB.visibility = View.INVISIBLE
        ber.visibility = View.INVISIBLE

        myTextView = findViewById(R.id.mytext)
        myTextView.visibility = View.VISIBLE

        rtfit = rFit.retrofit ?: run {
            Toast.makeText(this@AuthoraizationStrore,"Some Error Occured Try Again",Toast.LENGTH_SHORT).show()
            return
        }
        myItem = ""
        myproductList = mutableListOf()
        val productListService = rtfit.create(ProductListService::class.java)
        productListService.getProductList().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    myproductList = response.body() as MutableList<String>
                    if (myproductList != null) {
                        Toast.makeText(this@AuthoraizationStrore, "Loading...", Toast.LENGTH_SHORT)
                            .show()
                        setSpinner()
                    } else {
                        Toast.makeText(
                            this@AuthoraizationStrore,
                            "Some error Occured",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(this@AuthoraizationStrore, "Response Failed", Toast.LENGTH_SHORT)
                    .show()
            }
        })


        click = AnimationUtils.loadAnimation(this, R.anim.click)
        getDetailsBtn = findViewById(R.id.getDetails)
        getDetailsBtn.setOnClickListener {
            getDetailsBtn.startAnimation(click)
            sendAndReceiveData()
        }
        back = findViewById(R.id.mybbbtn)
    }
    fun onSimulateBackClick(view: View) {
        onBackPressed()
    }

    private fun sendAndReceiveData() {
        val storeService = rtfit.create(StoreService::class.java)
        val productTypeResponse = productTypeResponse(myItem)
        storeService.getData(productTypeResponse).enqueue(object : Callback<newStoreResponse> {
            override fun onResponse(call: Call<newStoreResponse>, response: Response<newStoreResponse>) {
                if (response.isSuccessful) {
                    val respo = response.body()
                    if (respo?.success == true) {
                        totalProducts = respo.totalProducts
                        HELD = respo.HELD
                        ISSUED = respo.ISSUED
                        SERVICEABLE = respo.SERVICEABLE
                        UNSERVICEABLE = respo.UNSERVICEABLE
                        BER = respo.BER
                        showPieChart()
                    }
                } else {
                    Toast.makeText(this@AuthoraizationStrore, "2", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<newStoreResponse>, t: Throwable) {
                Toast.makeText(this@AuthoraizationStrore, " response Failed", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun setSpinner() {

        if (myproductList != null) {
            items = mutableListOf()
            for (i in myproductList.indices) {
                items.add(myproductList[i])
            }
        }
        spinner = findViewById(R.id.authItem)
        myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = myAdapter
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                myItem = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })
    }

    private fun showPieChart() {
        if (totalProducts.toInt() != 0) {
            val issueP: Float = ISSUED.toFloat() / totalProducts.toFloat()
            val hld : Float = HELD.toFloat()/ totalProducts.toFloat()
            val svb :Float = SERVICEABLE.toFloat()/ totalProducts.toFloat()
            val unSvb : Float = UNSERVICEABLE.toFloat() / totalProducts.toFloat()
            val br :Float = BER.toFloat() / totalProducts.toFloat()

            pieChart.apply {
                slices = listOf(
                    PieChart.Slice(
                        issueP,
                        Color.rgb(120, 181, 0),
                        Color.rgb(149, 224, 0),
                        legend = "Legend A"
                    ),
                    PieChart.Slice(
                        hld,
                        Color.rgb(204, 168, 0),
                        Color.rgb(249, 228, 0),
                        legend = "Legend B"
                    ),
                    PieChart.Slice(
                        svb,
                        Color.rgb(0, 162, 216),
                        Color.rgb(31, 199, 255),
                        legend = "Legend C"
                    ),
                    PieChart.Slice(
                        unSvb,
                        Color.rgb(255, 4, 4),
                        Color.rgb(255, 72, 86),
                        legend = "Legend D"
                    ),
                    PieChart.Slice(
                        br,
                        Color.rgb(160, 165, 170),
                        Color.rgb(175, 180, 185),
                        legend = "Legend E"
                    )
                )
                startAngle = -90
                isCenterLabelEnabled = true
                centerLabel = "Authorization"
                centerLabelColor = Color.GRAY
                centerLabelIconTint = Color.rgb(159, 181, 114)
                legendsTitleColor = Color.MAGENTA
                legendsAlignment = Alignment.START
                centerLabelIcon = PieChart.DefaultIcons.SQUARE_HOLLOW
                labelIconsTint = Color.BLACK
                labelType = PieChart.LabelType.OUTSIDE
                isLegendsPercentageEnabled = false
                labelIconsPlacement = PieChart.IconPlacement.TOP
                gradientType = PieChart.GradientType.SWEEP
                holeRatio = 0.5f
            }



            pieChart2.apply {
                slices = listOf(
                    PieChart.Slice(
                        1.0f,
                        Color.parseColor("#c45b2c"),
                        label = "Total Product"
                    )
                )
                startAngle = -90
                isCenterLabelEnabled = true
                centerLabel = totalProducts
                centerLabelIcon = PieChart.DefaultIcons.SQUARE_HOLLOW
                centerLabelIconTint = Color.parseColor("#c45b2c")
                gradientType = PieChart.GradientType.SWEEP
                holeRatio = 0.6f

            }


            pieChart3.apply {
                slices = listOf(
                    PieChart.Slice(
                        1.0f,
                        Color.parseColor("#13677c")
                    )
                )
                startAngle = -90
                isCenterLabelEnabled = true
                centerLabel = myItem
                centerLabelIconTint = Color.parseColor("#13677c")
                centerLabelIcon = PieChart.DefaultIcons.SQUARE_HOLLOW
                gradientType = PieChart.GradientType.SWEEP
                holeRatio = 0.6f
            }
            issueBtn.text = "Issue\n$ISSUED"
            issueBtn.visibility = View.VISIBLE

            held.text = "Held\n$HELD"
            sB.text = "Serviceable\n$SERVICEABLE"
            uSB.text = "Unserviceable\n$UNSERVICEABLE"
            ber.text = "BER\n$BER"

            held.visibility = View.VISIBLE
            sB.visibility = View.VISIBLE
            uSB.visibility = View.VISIBLE
            ber.visibility = View.VISIBLE

            myTextView.visibility = View.INVISIBLE
            fLayout.visibility = View.VISIBLE
            linearLayout1.visibility = View.VISIBLE
            linearLayout2.visibility = View.VISIBLE

        } else {
           myTextView.text = "No Data Available for this Product"
        }
    }
}
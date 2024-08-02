package com.example.ncc_inventory

import android.app.Activity
import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NavUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class managerAdminProfile : AppCompatActivity() {

    private lateinit var myname : TextView
    private lateinit var pName : TextView
    private lateinit var pId : TextView
    private lateinit var pDesig : TextView
    private lateinit var pSection : TextView
    private lateinit var pAppoint : TextView
    private lateinit var pAll : TextView
    private lateinit var pDemand : TextView
    private lateinit var pIssue : TextView
    private lateinit var mybb : ImageView
    private lateinit var editicon : ImageView
    private lateinit var mName : String
    private lateinit var mPass : String
    private lateinit var mDesig : String
    private lateinit var mSection :String
    private lateinit var mAppont : String
    private lateinit var retroFit : Retrofit
    private lateinit var pureId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_admin_profile)


        myname = findViewById(R.id.Mname)
        pName = findViewById(R.id.pName)
        pId = findViewById(R.id.pId)
        pDesig = findViewById(R.id.pDesig)
        pSection = findViewById(R.id.pSecrion)
        pAppoint = findViewById(R.id.pAppoint)
        pAll = findViewById(R.id.pAllp)
        pDemand = findViewById(R.id.pDemand)
        pIssue = findViewById(R.id.pIssue)
        mybb = findViewById(R.id.mybb)
        retroFit = rFit.retrofit!!
        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        myname.text = intent.getStringExtra("name")
        pName.text = "Name :   ${intent.getStringExtra("name")}"
        pId.text = "Id :   ${intent.getStringExtra("id")}"
        pDesig.text ="Designation :   ${intent.getStringExtra("desig")}"
        pSection.text ="Section :   ${intent.getStringExtra("section")}"
        pAppoint.text = "Appointment :   ${intent.getStringExtra("appt")}"
        val check1 = intent.getBooleanExtra("isallproduct",false)
        val check2 = intent.getBooleanExtra("demandreceived",false)
        val check3 = intent.getBooleanExtra("issue",false)
        pureId = intent.getStringExtra("id").toString()

        if(check1){
            pAll.text = "All Product Report :   Allowed"
        }else{
            pAll.text = "All Product Report :   Not Allowed"
        }

        if(check2){
            pDemand.text = "Demand Received :   Allowed"
        }else{
            pDemand.text = "Demand Received :   Not Allowed"
        }

        if(check3){
           pIssue.text = "Issue Product :   Allowed"
        }else{
           pIssue.text = "Issue Product :   Not Allowed"
        }


        val click = AnimationUtils.loadAnimation(this,R.anim.click)
        mybb.setOnClickListener {
            mybb.startAnimation(click)
            goParentActivity()
        }
        editicon = findViewById(R.id.editIcon)
        editicon.setOnClickListener {
            editicon.startAnimation(click)
            var dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.edit_manager_)
            val managerName : EditText = dialog.findViewById(R.id.managerName)
            val managerPass : EditText = dialog.findViewById(R.id.managerPass)
            val managerDesignation : EditText = dialog.findViewById(R.id.managerDesignation)
            val managerSection : EditText = dialog.findViewById(R.id.managerSection)
            val managerAppointment : EditText = dialog.findViewById(R.id.managerAppointment)
            val allReport : Switch  = dialog.findViewById(R.id.allreport)
            val demandRecieved : Switch = dialog.findViewById(R.id.demandRecieved)
            val issueProduct : Switch = dialog.findViewById(R.id.issueProduct)
            var isProduct : Boolean = false
            var isDemand : Boolean = false
            var isIssue : Boolean = false

            managerName.setText(intent.getStringExtra("name"))
            managerPass.setText(intent.getStringExtra("password"))
            managerDesignation.setText(intent.getStringExtra("desig"))
            managerSection.setText(intent.getStringExtra("section"))
            managerAppointment.setText(intent.getStringExtra("appt"))
            allReport.isChecked = check1
            demandRecieved.isChecked = check2
            issueProduct.isChecked = check3

            val saveChanges : TextView = dialog.findViewById(R.id.submitButton)
            val deleteButton : TextView = dialog.findViewById(R.id.deleteBtn)
            saveChanges.setOnClickListener {
                saveChanges.startAnimation(click)
                mName = managerName.text.toString()
                mPass = managerPass.text.toString()
                mDesig = managerDesignation.text.toString()
                mSection = managerSection.text.toString()
                mAppont = managerAppointment.text.toString()
                if(allReport.isChecked){
                    isProduct = true
                }
                if(demandRecieved.isChecked){
                    isDemand = true
                }
                if(issueProduct.isChecked){
                    isIssue = true
                }
                val managerEdit = managerEdit(pureId,mName,mPass,mDesig,mSection,mAppont,isProduct,isDemand,isIssue)
                val managerEditService = retroFit.create(editManagerService::class.java)
                managerEditService.editManager(managerEdit).enqueue(object : Callback<ManagerResponse>{
                    override fun onResponse(
                        call: Call<ManagerResponse>,
                        response: Response<ManagerResponse>
                    ) {
                        if(response.isSuccessful){
                            val respo = response.body()
                            if(respo?.success == true){
                                Toast.makeText(this@managerAdminProfile,"Changes Saved",Toast.LENGTH_SHORT).show()
                                pureId = managerEdit.managerId
                                changePfData(managerEdit.managerName,managerEdit.managerId,managerEdit.password,managerEdit.designation,managerEdit.section,managerEdit.appointment,managerEdit.allProductReport,managerEdit.demandReceived,managerEdit.issueProduct)
                            }
                            else{
                                Toast.makeText(this@managerAdminProfile,"Save changes failed",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ManagerResponse>, t: Throwable) {
                        Toast.makeText(this@managerAdminProfile,"Response failed",Toast.LENGTH_SHORT).show()
                    }
                })
            }
            deleteButton.setOnClickListener {
                deleteButton.startAnimation(click)
                val deleteManager = deleteManager(pureId)
                val deleteManagerService = retroFit.create(DeleteManagerService::class.java)
                deleteManagerService.deleteManager(deleteManager).enqueue(object : Callback<ManagerResponse>{
                    override fun onResponse(
                        call: Call<ManagerResponse>,
                        response: Response<ManagerResponse>
                    ) {
                        if(response.isSuccessful){
                            val respo = response.body()
                            if(respo?.success == true){
                                Toast.makeText(this@managerAdminProfile,"Manager SuccessFully deleted",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                onBackPressed()
                            }
                            else{
                                Toast.makeText(this@managerAdminProfile,"Some error occurred",Toast.LENGTH_SHORT).show()
                            }
                        }

                    }

                    override fun onFailure(call: Call<ManagerResponse>, t: Throwable) {

                        Toast.makeText(this@managerAdminProfile,"Response failed.. Try Again",Toast.LENGTH_SHORT).show()
                    }
                })


            }
            dialog.show()
        }

    }
    private fun goParentActivity(){
            val parentIntent = NavUtils.getParentActivityIntent(this)
            if (parentIntent != null) {
                // Navigate to the parent activity
                NavUtils.navigateUpTo(this, parentIntent)
            } else {
                // Handle the case where no parent activity is specified (optional)
                finish()
            }
    }

    private fun changePfData(gUsername : String , gUserId : String ,gUserPass : String , gUserDesig : String , gUserSection : String, gUserAppt : String, check1 : Boolean,check2 : Boolean , check3 : Boolean){
        myname.text = gUsername
        pName.text = "Name :   $gUsername"
        pId.text = "Id :   $gUserId"
        pDesig.text ="Designation :   $gUserDesig"
        pSection.text ="Section :   $gUserSection"
        pAppoint.text = "Appointment :   $gUserAppt"

        if(check1){
            pAll.text = "All Product Report :   Allowed"
        }else{
            pAll.text = "All Product Report :   Not Allowed"
        }

        if(check2){
            pDemand.text = "Demand Received :   Allowed"
        }else{
            pDemand.text = "Demand Received :   Not Allowed"
        }

        if(check3){
            pIssue.text = "Issue Product :   Allowed"
        }else{
            pIssue.text = "Issue Product :   Not Allowed"
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }
}
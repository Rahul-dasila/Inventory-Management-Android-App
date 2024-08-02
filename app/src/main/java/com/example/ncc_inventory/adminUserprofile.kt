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
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NavUtils
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

class adminUserprofile : AppCompatActivity() {
    private lateinit var pName : TextView
    private lateinit var userName : TextView
    private lateinit var userId : TextView
    private lateinit var userDesig : TextView
    private lateinit var userSection : TextView
    private lateinit var userAppointment : TextView
    private lateinit var backbtn : ImageView
    private lateinit var editIcon : ImageView
    private lateinit var retroFT : Retrofit
    private lateinit var uPureId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_userprofile)

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        pName = findViewById(R.id.PnameU)
        userName = findViewById(R.id.userNamePf)
        userId = findViewById(R.id.userIdpf)
        userDesig = findViewById(R.id.userDesigpf)
        userSection = findViewById(R.id.userSectionpf)
        userAppointment = findViewById(R.id.userappt)

        val gUsername = intent.getStringExtra("name")
        val gUserId = intent.getStringExtra("id")
        val gUserPass = intent.getStringExtra("password")
        val gUserDesig = intent.getStringExtra("desig")
        val gUserAppt = intent.getStringExtra("appt")
        val gUserSection = intent.getStringExtra("section")
        uPureId = intent.getStringExtra("id").toString()
        pName.text = gUsername
        userName.text = gUsername
        userId.text = gUserId
        userDesig.text = gUserDesig
        userSection.text = gUserSection
        userAppointment.text = gUserAppt

        retroFT = rFit.retrofit!!

        val click = AnimationUtils.loadAnimation(this,R.anim.click)
        backbtn = findViewById(R.id.mybbUser)
        backbtn.setOnClickListener {
            backbtn.startAnimation(click)
            goToparentActivity()
        }

        editIcon = findViewById(R.id.editIconUser)
        editIcon.setOnClickListener {
            editIcon.startAnimation(click)
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.edit_user)
            val dUserName = dialog.findViewById<EditText>(R.id.userNamed)
            val dUserPass = dialog.findViewById<EditText>(R.id.userPassd)
            val dUserDesig = dialog.findViewById<EditText>(R.id.userDesignationd)
            val dUserSection = dialog.findViewById<EditText>(R.id.userSectiond)
            val dUserAppt = dialog.findViewById<EditText>(R.id.userAppointmentd)
            val saveChangesButton = dialog.findViewById<TextView>(R.id.saveChangesButton)
            val deleteUserButton = dialog.findViewById<TextView>(R.id.deleteUserBtn)


            dUserName.setText(gUsername)
            dUserPass.setText(gUserPass)
            dUserDesig.setText(gUserDesig)
            dUserSection.setText(gUserSection)
            dUserAppt.setText(gUserAppt)


            saveChangesButton.setOnClickListener {
                saveChangesButton.startAnimation(click)
                val sUserName = dUserName.text.toString()
                val sUserPass = dUserPass.text.toString()
                val sUserDesig =dUserDesig.text.toString()
                val sUserSection = dUserSection.text.toString()
                val sUserAppt = dUserAppt.text.toString()
                val editedUser = EditedUser(uPureId,sUserName,sUserPass,sUserDesig,sUserSection,sUserAppt)
                val editUserService = retroFT.create(EditUserService::class.java)
                editUserService.editUser(editedUser).enqueue(object : Callback<EditUserResponse>{
                    override fun onResponse(
                        call: Call<EditUserResponse>,
                        response: Response<EditUserResponse>
                    ) {
                        if(response.isSuccessful){
                            val respo = response.body()
                            if(respo?.success == true){
                                Toast.makeText(this@adminUserprofile,"Changes Saved",Toast.LENGTH_SHORT).show()
                                uPureId = editedUser.userId
                                chageProfileData(editedUser.userName,editedUser.userId,editedUser.password,editedUser.designation,editedUser.section,editedUser.appointment)
                            }
                            else{
                                Toast.makeText(this@adminUserprofile,"Error : Try Again",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<EditUserResponse>, t: Throwable) {
                        Toast.makeText(this@adminUserprofile,"Response failed Pls Check your connection",Toast.LENGTH_SHORT).show()
                    }
                })
            }

            deleteUserButton.setOnClickListener {
                deleteUserButton.startAnimation(click)
                val deletedUser = deleteUser(uPureId)
                val deleteUserService = retroFT.create(DeleteUserService::class.java)
                deleteUserService.deleteUser(deletedUser).enqueue(object : Callback<EditUserResponse>{
                    override fun onResponse(
                        call: Call<EditUserResponse>,
                        response: Response<EditUserResponse>
                    ) {
                        if(response.isSuccessful){
                            val respo = response.body()
                            if(respo?.success == true){
                                Toast.makeText(this@adminUserprofile,"User Deleted",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                onBackPressed()
                            }
                            else{
                                Toast.makeText(this@adminUserprofile,"Action Failed",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<EditUserResponse>, t: Throwable) {
                        Toast.makeText(this@adminUserprofile,"Response failed",Toast.LENGTH_SHORT).show()
                    }
                })
            }
            dialog.show()

        }

    }

    private fun goToparentActivity() {
        val parentIntent = NavUtils.getParentActivityIntent(this)
        if (parentIntent != null) {
            // Navigate to the parent activity
            NavUtils.navigateUpTo(this, parentIntent)
        } else {
            // Handle the case where no parent activity is specified (optional)
            finish()
        }
    }

    private fun chageProfileData(gUsername : String , gUserId : String ,gUserPass : String , gUserDesig : String , gUserSection : String, gUserAppt : String){
        pName.text = gUsername
        userName.text = gUsername
        userId.text = gUserId
        userDesig.text = gUserDesig
        userSection.text = gUserSection
        userAppointment.text = gUserAppt
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }
}
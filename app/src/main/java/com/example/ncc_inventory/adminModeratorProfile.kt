package com.example.ncc_inventory

import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class adminModeratorProfile : AppCompatActivity() {
    private lateinit var name: TextView
    private lateinit var name2: TextView
    private lateinit var id: TextView
    private lateinit var desig: TextView
    private lateinit var appt: TextView
    private lateinit var section: TextView
    private lateinit var editBtn: ImageView
    private lateinit var retrofit: Retrofit
    private lateinit var checkId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_moderator_profile)
        name = findViewById(R.id.modName)
        name2 = findViewById(R.id.mNameU)
        id = findViewById(R.id.modId)
        desig = findViewById(R.id.modDesig)
        appt = findViewById(R.id.modappt)
        section = findViewById(R.id.modSection)
        editBtn = findViewById(R.id.editIconUserMod)
        retrofit = rFit.retrofit!!

        //For transparent status bar
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        name.text = intent.getStringExtra("name")
        name2.text = intent.getStringExtra("name")
        id.text = intent.getStringExtra("id")
        desig.text = intent.getStringExtra("desig")
        appt.text = intent.getStringExtra("appt")
        section.text = intent.getStringExtra("section")


        checkId = intent.getStringExtra("id").toString()
        val gUsername = intent.getStringExtra("name")
        val gUserId = intent.getStringExtra("id")
        val gUserPass = intent.getStringExtra("pass")
        val gUserDesig = intent.getStringExtra("desig")
        val gUserAppt = intent.getStringExtra("appt")
        val gUserSection = intent.getStringExtra("section")


        editBtn.setOnClickListener {
            editBtn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.click))
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.edit_moderator)
            val dUserName = dialog.findViewById<EditText>(R.id.modNameE)
            val dUserDesig = dialog.findViewById<EditText>(R.id.modDesignationE)
            val dUserSection = dialog.findViewById<EditText>(R.id.modSectionE)
            val dUserAppt = dialog.findViewById<EditText>(R.id.modAppointmentE)
            val saveChangesButton = dialog.findViewById<TextView>(R.id.saveChangesButtonMod)
            val deleteUserButton = dialog.findViewById<TextView>(R.id.deleteModBtn)


            dUserName.setText(gUsername)
            dUserDesig.setText(gUserDesig)
            dUserSection.setText(gUserSection)
            dUserAppt.setText(gUserAppt)

            saveChangesButton.setOnClickListener {
                saveChangesButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.click))
                val sUserName = dUserName.text.toString()
                val sUserDesig = dUserDesig.text.toString()
                val sUserSection = dUserSection.text.toString()
                val sUserAppt = dUserAppt.text.toString()
                val editedModerator = gUserId?.let { it1 ->
                    edit_moderator_request(
                        it1,
                        sUserName,
                        sUserDesig,
                        sUserSection,
                        sUserAppt
                    )
                }
                val service = retrofit.create(edit_moderator_service::class.java)
                if (editedModerator != null) {
                    service.edituser(editedModerator)
                        .enqueue(object : Callback<edit_moderator_response> {
                            override fun onResponse(
                                call: Call<edit_moderator_response>,
                                response: Response<edit_moderator_response>
                            ) {
                                if (response.isSuccessful) {
                                    val respo = response.body()
                                    if (respo?.success == true) {
                                        Toast.makeText(
                                            this@adminModeratorProfile,
                                            "Changes Saved",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        checkId = editedModerator.moderatorId
                                        chageProfileData(
                                            respo.moderator.moderatorName,
                                            respo.moderator.moderatorId,
                                            respo.moderator.designation,
                                            respo.moderator.section,
                                            respo.moderator.appointment
                                        )

                                    } else {
                                        Toast.makeText(
                                            this@adminModeratorProfile,
                                            "Error : Try Again",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                } else {
                                    Toast.makeText(
                                        this@adminModeratorProfile,
                                        "Error : Try Again",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(
                                call: Call<edit_moderator_response>,
                                t: Throwable
                            ) {
                                Toast.makeText(
                                    this@adminModeratorProfile,
                                    "Error : Some error occurred",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                }

            }

            deleteUserButton.setOnClickListener {
                deleteUserButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.click))
                val service = retrofit.create(edit_moderator_service::class.java)
                val mod = gUserId?.let { it1 -> deleteModerator(it1) }
                if (mod != null) {
                    service.deleteModerator(mod).enqueue(object : Callback<deleteModeratorResponsee>{
                        override fun onResponse(
                            call: Call<deleteModeratorResponsee>,
                            response: Response<deleteModeratorResponsee>
                        ) {
                            if(response.isSuccessful){
                                val respo = response.body()
                                if(respo?.success == true){
                                    Toast.makeText(this@adminModeratorProfile,"Successfully Deleted",Toast.LENGTH_SHORT).show()
                                    onBackPressed()
                                }

                            }else{
                                Toast.makeText(
                                    this@adminModeratorProfile,
                                    "Error : Try Again",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<deleteModeratorResponsee>, t: Throwable) {
                            Toast.makeText(
                                this@adminModeratorProfile,
                                "Error : Some error occurred",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                }
            }

            dialog.show()

        }
    }

    private fun chageProfileData(
        gUsername: String,
        gUserId: String,
        gUserDesig: String,
        gUserSection: String,
        gUserAppt: String
    ) {
        name.text = gUsername
        name2.text = gUsername
        id.text = gUserId
        desig.text = gUserDesig
        appt.text = gUserSection
        section.text = gUserAppt
    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        val it = Intent(this@adminModeratorProfile, Moderator_list::class.java)
        startActivity(it)
        finish()
        super.onBackPressed()
    }
}
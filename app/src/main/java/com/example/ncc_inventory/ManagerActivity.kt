package com.example.ncc_inventory

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ncc_inventory.databinding.ActivityManagerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ManagerActivity : AppCompatActivity(){
    private lateinit var retrofit : Retrofit
    private lateinit var binding : ActivityManagerBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retrofit = rFit.retrofit!!


        binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(addManagerFragement())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.addM -> replaceFragment(addManagerFragement() )
                R.id.managerL -> replaceFragment(managerListFragment.newInstance("param1", "param2"))
                else -> {
                    false
                }
            }
            true
        }

    }

    private fun replaceFragment ( fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()

        if (fragment is managerListFragment) {
            showManager(fragment)
        }
    }

    fun handleManagerData(
        name: String,
        email :String,
        pass: String,
        id: String,
        designation: String,
        section: String,
        appointment: String,
        remark: String,
        isAllReport: Boolean,
        isDemandReceived: Boolean,
        isIssueProduct: Boolean
    ) {
       val manager = Manager(id,email,name,pass,designation,section,appointment,remark,isAllReport,isDemandReceived,isIssueProduct)
       addManger(manager)
    }


    private fun addManger(manager: Manager){
        var mS = retrofit.create(managerService::class.java)
        mS.addManager(manager).enqueue(object :Callback<ManagerResponse>{
            override fun onResponse(call: Call<ManagerResponse>, response: Response<ManagerResponse>) {
                if(response.isSuccessful){
                    val managerResponse = response.body()
                    if(managerResponse?.success==true){
                        Toast.makeText(this@ManagerActivity, "Manager added successfully!", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(this@ManagerActivity, managerResponse?.message ?: "Manager addition failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else {
                    Toast.makeText(this@ManagerActivity, "Failed to add Manager: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ManagerResponse>, t: Throwable) {
                Toast.makeText(this@ManagerActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showManager(fragment: managerListFragment){
        val arrayList = arrayListOf<adapterManagerItem>()
        val managerListService = retrofit.create(managerListService::class.java)
        managerListService.getManagers().enqueue(object : Callback<List<managerDataclassFor>>{
            override fun onResponse(
                call: Call<List<managerDataclassFor>>,
                response: Response<List<managerDataclassFor>>
            ) {
                if(response.isSuccessful){
                    val managers = response.body()
                    if (managers != null) {


                        for(i in managers.indices){
                            var obj: adapterManagerItem = adapterManagerItem(managers[i].managerId,managers[i].managerName,managers[i].password,managers[i].designation,managers[i].section,managers[i].appointment,managers[i].allProductReport,managers[i].demandReceived,managers[i].issueProduct)
                            arrayList.add(obj)
                        }
                            fragment.setArrayList(arrayList)
                            Toast.makeText(this@ManagerActivity,"Successfull response",Toast.LENGTH_SHORT).show()

                    }
                } else {

                        Toast.makeText(this@ManagerActivity,"Response failed",Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<List<managerDataclassFor>>, t: Throwable) {
                    Toast.makeText(this@ManagerActivity,"Error : $t",Toast.LENGTH_SHORT).show()
            }
        })
    }
    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        const val REQUEST_CODE_PROFILE = 1002
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ManagerActivity.REQUEST_CODE_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                // The user returned from ProfileActivity, refresh the fragment
                val fragment = supportFragmentManager.findFragmentById(R.id.frameLayout)

                if (fragment is managerListFragment) {

                    showManager(fragment)
                }
            }
        }
    }
}
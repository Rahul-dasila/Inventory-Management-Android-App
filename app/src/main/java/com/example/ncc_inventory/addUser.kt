package com.example.ncc_inventory

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ncc_inventory.databinding.ActivityAddUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

class addUser : AppCompatActivity() {


    private lateinit var retrofit : Retrofit
    private lateinit var binding : ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        retrofit = rFit.retrofit!!


        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(addUserFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.addM -> replaceFragment(addUserFragment() )
                R.id.managerL -> replaceFragment(userListFragment.newInstance("param1", "param2"))
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
        fragmentTransaction.replace(R.id.frameLayout2,fragment)
        fragmentTransaction.commit()

        if (fragment is userListFragment) {
            showUser(fragment)
        }
    }

    fun handleUserData(
        name: String,
        mail : String,
        pass: String,
        id: String,
        designation: String,
        section: String,
        appointment: String,
        remark: String
    ) {
        val user = User(id,mail,name,pass,designation,section,appointment,remark)
        addusr(user)
    }
    private fun addusr(user : User){
        val userService = retrofit.create(UserService::class.java)
        userService.addnewUser(user).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success== true){
                        Toast.makeText(this@addUser,"User Successfully Added",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@addUser,"Error adding user. Try again",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@addUser,"Response Failed",Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun showUser(fragment: userListFragment){
        val arrayList = arrayListOf<adapterUserItem>()
        val userListService = retrofit.create(UserListService :: class.java)
        userListService.getUsers().enqueue(object : Callback<List<adapterUserItem>>{
            override fun onResponse(
                call: Call<List<adapterUserItem>>,
                response: Response<List<adapterUserItem>>
            ) {
                if(response.isSuccessful){
                    val users = response.body()
                    if(users!=null){
                        for(i in users.indices){
                            val obj = adapterUserItem(users[i].userId,users[i].userName,users[i].password,users[i].designation,users[i].section,users[i].appointment)
                            arrayList.add(obj)
                        }
                        fragment.setArrayList(arrayList)
                        Toast.makeText(this@addUser,"Successfull response",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<adapterUserItem>>, t: Throwable) {
                Toast.makeText(this@addUser,"Response Failed",Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        const val REQUEST_CODE_PROFILE = 1001
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                // The user returned from ProfileActivity, refresh the fragment
                val fragment = supportFragmentManager.findFragmentById(R.id.frameLayout2)
                if (fragment is userListFragment) {
                    showUser(fragment)
                }
            }
        }
    }
}
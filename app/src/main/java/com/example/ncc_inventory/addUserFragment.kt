package com.example.ncc_inventory

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.ncc_inventory.databinding.FragmentAddUserBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class addUserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding : FragmentAddUserBinding ? =null
    private val binding get() = _binding!!
    private lateinit var click : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val submitbtn = binding.submitButton2
        val userName = binding.userName
        val userId = binding.userId
        val userPass = binding.userPass
        val userDesignation = binding.userDesignation
        val userSection = binding.userSection
        val userAppointment = binding.userAppointment
        val userRemark = binding.userRemark
        val userMail = binding.userMail
        context.let {
             click = AnimationUtils.loadAnimation(it,R.anim.click)
        }
        submitbtn.setOnClickListener {
            submitbtn.startAnimation(click)
            val name = userName.text.toString()
            val pass = userPass.text.toString()
            val id = userId.text.toString()
            val designation = userDesignation.text.toString()
            val section = userSection.text.toString()
            val appointment = userAppointment.text.toString()
            val remark1 = userRemark.text.toString()
            val mail = userMail.text.toString()
            handleSubmitButtonClick(name,mail,pass,id,designation,section,appointment,remark1)
            userName.text.clear()
            userPass.text.clear()
            userId.text.clear()
            userMail.text.clear()
            userDesignation.text.clear()
            userSection.text.clear()
            userAppointment.text.clear()
            userRemark.text.clear()
        }
    }

    private fun handleSubmitButtonClick(
        name: String,
        mail : String,
        pass: String,
        id: String,
        designation: String,
        section: String,
        appointment: String,
        remark: String
    ) {
        (activity as? addUser)?.handleUserData(
            name,
            mail,
            pass,
            id,
            designation,
            section,
            appointment,
            remark
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment addUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            addUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
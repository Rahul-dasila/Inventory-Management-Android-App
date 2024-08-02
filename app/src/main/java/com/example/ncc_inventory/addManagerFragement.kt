package com.example.ncc_inventory

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NavUtils
import com.example.ncc_inventory.databinding.FragmentAddManagerFragementBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addManagerFragement.newInstance] factory method to
 * create an instance of this fragment.
 */
class addManagerFragement : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddManagerFragementBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentAddManagerFragementBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val submitButton = binding.submitButton
        val managerName = binding.managerName
        val managerEmail = binding.managerEmail
        val managerPass = binding.managerPass
        val managerId = binding.managerId
        val managerDesignation = binding.managerDesignation
        val managerSection = binding.managerSection
        val managerAppointment = binding.managerAppointment
        val remark = binding.remark
        val allReport = binding.allreport
        val demandReceived  = binding.demandRecieved
        val issueProduct = binding.issueProduct

        submitButton.setOnClickListener {
            val name = managerName.text.toString()
            val pass = managerPass.text.toString()
            val id = managerId.text.toString()
            val email = managerEmail.text.toString()
            val designation = managerDesignation.text.toString()
            val section = managerSection.text.toString()
            val appointment = managerAppointment.text.toString()
            val remark1 = remark.text.toString()
            val isAllReport = allReport.isChecked
            val isDemandReceived = demandReceived.isChecked
            val isIssueProduct = issueProduct.isChecked
           handleSubmitButtonClick(name,email,pass,id,designation,section,appointment,remark1,isAllReport,isDemandReceived,isIssueProduct)
            managerName.text.clear()
            managerPass.text.clear()
            managerId.text.clear()
            managerDesignation.text.clear()
            managerSection.text.clear()
            managerAppointment.text.clear()
            remark.text.clear()
            allReport.isChecked = false
            demandReceived.isChecked = false
            issueProduct.isChecked = false
        }
//        context.let {
//            val click = AnimationUtils.loadAnimation(it, R.anim.click)
//
//            bb.setOnClickListener {
//                bb.startAnimation(click)
//                val activity = requireActivity() // or activity if inside a Fragment
//
//                val parentIntent = NavUtils.getParentActivityIntent(activity)
//                if (parentIntent != null) {
//                    // Navigate to the parent activity
//                    NavUtils.navigateUpTo(activity, parentIntent)
//                } else {
//                    // Handle the case where no parent activity is specified (optional)
//                    Toast.makeText(activity, "Button is under service", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    private fun handleSubmitButtonClick(
        name: String,
        email : String,
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
        (activity as? ManagerActivity)?.handleManagerData(
            name,
            email,
            pass,
            id,
            designation,
            section,
            appointment,
            remark,
            isAllReport,
            isDemandReceived,
            isIssueProduct
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
         * @return A new instance of fragment addManagerFragement.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            addManagerFragement().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
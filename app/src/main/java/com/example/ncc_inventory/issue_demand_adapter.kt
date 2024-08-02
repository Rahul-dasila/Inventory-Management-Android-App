package com.example.ncc_inventory

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.Locale

class issue_demand_adapter(val context: Context , val adapterItems : List<demands>,val retrofit: Retrofit,val textView: TextView) : RecyclerView.Adapter<issue_demand_adapter.ViewHolderClass>() {

    private var filteredUsers: List<demands> = adapterItems


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.issue_productitem,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = filteredUsers[position]
        holder.createdAt.text = currentItem.createdAt
        holder.demandId.text = currentItem.demandId
        holder.userId.text = currentItem.userId
        holder.designation.text = currentItem.designation
        holder.productName.text = currentItem.productName
        holder.productType.text = currentItem.productType
        holder.productModel.text = currentItem.productModel
        holder.productBrand.text = currentItem.productBrand
        holder.additionalDetail.text = currentItem.additionalDetail
        holder.status.text = currentItem.status
        holder.productQuantity.text = currentItem.productQuantity.toString()
        holder.issueBtn.setOnClickListener {
            holder.issueBtn.startAnimation(AnimationUtils.loadAnimation(context,R.anim.click))
            val intent = Intent(context,filterUserAssign::class.java)
            intent.putExtra("type",currentItem.productType)
            intent.putExtra("model",currentItem.productModel)
            intent.putExtra("brand",currentItem.productBrand)
            intent.putExtra("userId",currentItem.userId)
            intent.putExtra("demandId", currentItem.demandId)
            intent.putExtra("quantity",currentItem.productQuantity)
            context.startActivity(intent)


        }
    }



    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            adapterItems
        }else{
            adapterItems.filter {
                it.productType.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || it.productName.lowercase(
                    Locale.ROOT).contains(query.lowercase(Locale.ROOT))||it.userId.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))
            }
        }
        notifyDataSetChanged()
    }
    class ViewHolderClass (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val createdAt : TextView = itemView.findViewById<TextView>(R.id.dOtimeMod)
        val demandId : TextView = itemView.findViewById<TextView>(R.id.dmndIdMod)
        val userId :  TextView = itemView.findViewById<TextView>(R.id.usrIdMod)
        val designation :  TextView = itemView.findViewById<TextView>(R.id.dsgMod)
        val productName :  TextView = itemView.findViewById<TextView>(R.id.pnameMod)
        val productType :  TextView = itemView.findViewById<TextView>(R.id.pdtypeMod)
        val productModel :  TextView = itemView.findViewById<TextView>(R.id.pdmodelMod)
        val productBrand : TextView = itemView.findViewById<TextView>(R.id.bandMod)
        val additionalDetail : TextView = itemView.findViewById<TextView>(R.id.adtMod)
        val productQuantity :  TextView = itemView.findViewById<TextView>(R.id.qutMod)
        val status :  TextView = itemView.findViewById<TextView>(R.id.pdstatusMod)
        val issueBtn : TextView = itemView.findViewById(R.id.myIssueBtn)
    }
}
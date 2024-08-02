package com.example.ncc_inventory

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class orgPendingDEmandAdapter(val context: Context, val adapterItems : List<pendingDemands>) : RecyclerView.Adapter<orgPendingDEmandAdapter.ViewHolderClass>() {

    private var filteredUsers: List<pendingDemands> = adapterItems
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pending_demand_item,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentUser = filteredUsers[position]
        holder.demandId.text = currentUser.demandId
        holder.companyId.text = currentUser.companyId
        holder.productType.text = currentUser.productType
        holder.productName.text = currentUser.productName
        holder.productModel.text = currentUser.productModel
        holder.additionalDetail.text = currentUser.additionalDetail
        holder.productQuantity.text = currentUser.productQuantity.toString()
        holder.status.text = currentUser.status
        holder.createdAt.text = currentUser.createdAt
    }

    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            adapterItems
        }else{
            adapterItems.filter {
                it.productName.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || it.companyId.lowercase(
                    Locale.ROOT).contains(query.lowercase(Locale.ROOT))
            }
        }
        notifyDataSetChanged()
    }

    class ViewHolderClass(itemView : View) :  RecyclerView.ViewHolder(itemView) {
        val demandId = itemView.findViewById<TextView>(R.id.pendingDmdId)
        val companyId = itemView.findViewById<TextView>(R.id.pendingCompanyId)
        val productType = itemView.findViewById<TextView>(R.id.pendingtype)
        val productName = itemView.findViewById<TextView>(R.id.pendingname)
        val productModel= itemView.findViewById<TextView>(R.id.pendingmodel)
        val additionalDetail= itemView.findViewById<TextView>(R.id.pendingadt1)
        val productQuantity= itemView.findViewById<TextView>(R.id.pendingqut)
        val status= itemView.findViewById<TextView>(R.id.pendingsts)
        val createdAt = itemView.findViewById<TextView>(R.id.PendingDate)
    }

}
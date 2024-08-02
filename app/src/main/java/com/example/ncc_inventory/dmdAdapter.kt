package com.example.ncc_inventory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class dmdAdapter(val context: Context , val adapterItems : List<dmdRequestedItems>) : RecyclerView.Adapter<dmdAdapter.ViewHolderClass>() {

    private var filteredUsers: List<dmdRequestedItems> = adapterItems


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_demands,parent,false)
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
        val createdAt : TextView = itemView.findViewById<TextView>(R.id.dOtime)
        val demandId : TextView = itemView.findViewById<TextView>(R.id.dmndId)
        val userId :  TextView = itemView.findViewById<TextView>(R.id.usrId)
        val designation :  TextView = itemView.findViewById<TextView>(R.id.dsg)
        val productName :  TextView = itemView.findViewById<TextView>(R.id.pname)
        val productType :  TextView = itemView.findViewById<TextView>(R.id.pdtype)
        val productModel :  TextView = itemView.findViewById<TextView>(R.id.pdmodel)
        val productBrand : TextView = itemView.findViewById<TextView>(R.id.band)
        val additionalDetail : TextView = itemView.findViewById<TextView>(R.id.adt)
        val productQuantity :  TextView = itemView.findViewById<TextView>(R.id.qut)
        val status :  TextView = itemView.findViewById<TextView>(R.id.pdstatus)
    }
}
package com.example.ncc_inventory

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import java.util.Locale

class statusDemandAdapter(private var requestStatus : List<requestStatus>) : RecyclerView.Adapter<statusDemandAdapter.ViewHolderClass>(){

    private var filteredUsers: List<requestStatus> = requestStatus

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.deamand_status,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = filteredUsers[position]
        holder.dateTime.text = currentItem.createdAt
        holder.dmdId.text = currentItem.demandId
        holder.type.text = currentItem.productType
        holder.pdname.text = currentItem.productName
        holder.model.text = currentItem.productModel
        holder.qt.text = currentItem.productQuantity.toString()
        if(currentItem.status.trim().lowercase() == "pending") {
            holder.status.setTextColor(Color.parseColor("#FFBF00"))
            holder.status.text = currentItem.status
        }else if(currentItem.status.trim().lowercase() == "approved"){
            holder.status.setTextColor(Color.GREEN)
            holder.status.text = currentItem.status
        }
        else if(currentItem.status.trim().lowercase() == "rejected"){
            holder.status.setTextColor(Color.RED)
            holder.status.text = currentItem.status
        }
    }
    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            requestStatus
        }else{
            requestStatus.filter {
                it.productName.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || it.productType.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))||it.productQuantity.toString().lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))
            }
        }
        notifyDataSetChanged()
    }

    class ViewHolderClass(itemView : View): RecyclerView.ViewHolder(itemView) {
        val dateTime = itemView.findViewById<TextView>(R.id.dateortime)
        val dmdId = itemView.findViewById<TextView>(R.id.dmdId)
        val type = itemView.findViewById<TextView>(R.id.type)
        val pdname = itemView.findViewById<TextView>(R.id.pdname)
        val model = itemView.findViewById<TextView>(R.id.model)
        val qt = itemView.findViewById<TextView>(R.id.qt)
        var status = itemView.findViewById<TextView>(R.id.status)
    }
}
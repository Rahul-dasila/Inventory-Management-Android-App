package com.example.ncc_inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class storeReportAdapter(val list: List<Report>) :
    RecyclerView.Adapter<storeReportAdapter.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.store_report_element,parent,false)
       return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = list[position]
        holder.productId.text = currentItem.productId
        holder.productBrand.text = currentItem.productBrand
        holder.status.text= currentItem.status
        holder.dateAndTime.text= currentItem.createdAt
        holder.productName.text = currentItem.productName
        holder.productModel.text = currentItem.productModel
        holder.type.text = currentItem.productType
    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productId = itemView.findViewById<TextView>(R.id.productId)
        val dateAndTime = itemView.findViewById<TextView>(R.id.dAndtime)
        val type = itemView.findViewById<TextView>(R.id.pdtype)
        val productName = itemView.findViewById<TextView>(R.id.pname)
        val productModel = itemView.findViewById<TextView>(R.id.pdmodel)
        val productBrand = itemView.findViewById<TextView>(R.id.band)
        val status = itemView.findViewById<TextView>(R.id.pdstatus)
    }
}
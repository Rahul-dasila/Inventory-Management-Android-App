package com.example.ncc_inventory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class allProductReportAdapter (val context: Context, val adapterItems : List<products>): RecyclerView.Adapter<allProductReportAdapter.ViewHolderClass>() {
    private var filteredUsers: List<products> = adapterItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.all_product_item,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = filteredUsers[position]
        holder.productId.text = currentItem.productId
        holder.issuedTo.text = currentItem.issuedTo
        holder.productType.text = currentItem.productType
        holder.productName.text = currentItem.productName
        holder.productBrand.text = currentItem.productBrand
        holder.productModel.text = currentItem.productModel
        holder.price.text = currentItem.productPrice.toString()
        holder.ad.text = currentItem.additionalDetail
        holder.sts.text = currentItem.status
    }

    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            adapterItems
        }else{
            adapterItems.filter {
                it.productType.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || it.productName.lowercase(
                    Locale.ROOT).contains(query.lowercase(Locale.ROOT))||it.issuedTo.lowercase(
                    Locale.ROOT).contains(query.lowercase(
                    Locale.ROOT))
            }
        }
        notifyDataSetChanged()
    }

    class ViewHolderClass(itemView : View) : RecyclerView.ViewHolder(itemView)  {
        val productId = itemView.findViewById<TextView>(R.id.id)
        val issuedTo = itemView.findViewById<TextView>(R.id.issuedTo)
        val productType = itemView.findViewById<TextView>(R.id.type)
        val productName = itemView.findViewById<TextView>(R.id.name)
        val productBrand = itemView.findViewById<TextView>(R.id.brand)
        val productModel = itemView.findViewById<TextView>(R.id.model)
        val price = itemView.findViewById<TextView>(R.id.price)
        val ad = itemView.findViewById<TextView>(R.id.adt1)
        val sts = itemView.findViewById<TextView>(R.id.sts)
    }

}
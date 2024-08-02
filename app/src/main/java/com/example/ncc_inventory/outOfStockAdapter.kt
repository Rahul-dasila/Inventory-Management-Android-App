package com.example.ncc_inventory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class outOfStockAdapter(val context: Context,val adapterItems : List<outOfStockDemands>): RecyclerView.Adapter<outOfStockAdapter.ViewHolderClass>() {
    private var filteredUsers: List<outOfStockDemands> = adapterItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.out_of_stock_item,parent,false)
        return outOfStockAdapter.ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
     val currentItem = filteredUsers[position]
        holder.productModel.text = currentItem.productModel
        holder.productBrand.text = currentItem.productBrand
        holder.productType.text = currentItem.productType
        holder.av.text = currentItem.availableQuantity.toString()
        holder.td.text  = currentItem.totalDemandQuantity.toString()
    }

    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            adapterItems
        }else{
            adapterItems.filter {
                it.productType.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || it.productModel.lowercase(
                    Locale.ROOT).contains(query.lowercase(Locale.ROOT))||it.productBrand.lowercase(Locale.ROOT).contains(query.lowercase(
                    Locale.ROOT))
            }
        }
        notifyDataSetChanged()
    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val productType = itemView.findViewById<TextView>(R.id.pdtypeoss)
        val productModel = itemView.findViewById<TextView>(R.id.pdmodeloss)
        val productBrand = itemView.findViewById<TextView>(R.id.bandoss)
        val td = itemView.findViewById<TextView>(R.id.totalDE)
        val av = itemView.findViewById<TextView>(R.id.Avail)
    }

}
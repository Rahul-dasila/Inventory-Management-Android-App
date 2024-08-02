package com.example.ncc_inventory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class Issued_ProductsAdapter (val context: Context, val adapterItems : List<Issued_products>): RecyclerView.Adapter<Issued_ProductsAdapter.ViewHolderClass>() {
    private var filteredUsers: List<Issued_products> = adapterItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.issue_report_item,parent,false)
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
        val productId = itemView.findViewById<TextView>(R.id.Iid)
        val issuedTo = itemView.findViewById<TextView>(R.id.IissuedTo)
        val productType = itemView.findViewById<TextView>(R.id.Itype)
        val productName = itemView.findViewById<TextView>(R.id.Iname)
        val productBrand = itemView.findViewById<TextView>(R.id.Ibrand)
        val productModel = itemView.findViewById<TextView>(R.id.Imodel)
        val price = itemView.findViewById<TextView>(R.id.Iprice)
        val ad = itemView.findViewById<TextView>(R.id.Iadt1)
            }

}
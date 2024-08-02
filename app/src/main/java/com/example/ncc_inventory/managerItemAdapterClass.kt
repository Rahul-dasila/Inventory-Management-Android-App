package com.example.ncc_inventory

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale


class managerItemAdapterClass(var context : Activity,private var adapterManagerItem: ArrayList<adapterManagerItem>) :RecyclerView.Adapter<managerItemAdapterClass.ViewHolderClass>(){

    private var filteredManagers: List<adapterManagerItem> = adapterManagerItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_adapter,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return filteredManagers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = filteredManagers[position]
        holder.rvDesig.text = currentItem.designation
        holder.rvId.text = currentItem.id
        holder.rvName.text = currentItem.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context,managerAdminProfile::class.java)
            intent.putExtra("name",currentItem.name)
            intent.putExtra("id",currentItem.id)
            intent.putExtra("password",currentItem.password)
            intent.putExtra("desig",currentItem.designation)
            intent.putExtra("section",currentItem.section)
            intent.putExtra("appt",currentItem.appointment)
            intent.putExtra("isallproduct",currentItem.allProductReport)
            intent.putExtra("demandreceived",currentItem.demandReceived)
            intent.putExtra("issue",currentItem.issueProduct)

            context.startActivityForResult(intent,ManagerActivity.REQUEST_CODE_PROFILE)
        }

    }

    fun filter(query: String) {
        filteredManagers = if (query.isEmpty()) {
            adapterManagerItem
        }else{
           adapterManagerItem.filter {
               it.name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || it.id.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))
           }
        }
        notifyDataSetChanged()
    }

    class ViewHolderClass(itemView : View): RecyclerView.ViewHolder(itemView) {
        val rvId : TextView = itemView.findViewById(R.id.managerId111)
        val rvName : TextView = itemView.findViewById(R.id.managerName111)
        val rvDesig : TextView = itemView.findViewById(R.id.managerDesig)
    }
}

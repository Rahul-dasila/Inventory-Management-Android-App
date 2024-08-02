package com.example.ncc_inventory

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class addUserAdapter(var context : Activity, private var adapterUserItem: ArrayList<adapterUserItem>) : RecyclerView.Adapter<addUserAdapter.ViewHolderClass>(){
    private var filteredUsers: List<adapterUserItem> = adapterUserItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_lisr_adapter,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = filteredUsers[position]
        holder.rvDesig.text = currentItem.designation
        holder.rvId.text = currentItem.userId
        holder.rvName.text = currentItem.userName
        holder.itemView.setOnClickListener {
            val intent = Intent(context,adminUserprofile::class.java)
            intent.putExtra("name",currentItem.userName)
            intent.putExtra("id",currentItem.userId)
            intent.putExtra("password",currentItem.password)
            intent.putExtra("desig",currentItem.designation)
            intent.putExtra("section",currentItem.section)
            intent.putExtra("appt",currentItem.appointment)
            context.startActivityForResult(intent,addUser.REQUEST_CODE_PROFILE)
        }

    }

    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            adapterUserItem
        }else{
            adapterUserItem.filter {
                it.userName.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || it.userId.lowercase(
                    Locale.ROOT).contains(query.lowercase(Locale.ROOT))
            }
        }
        notifyDataSetChanged()
    }

    class ViewHolderClass(itemView : View): RecyclerView.ViewHolder(itemView) {
        val rvId : TextView = itemView.findViewById(R.id.userId111)
        val rvName : TextView = itemView.findViewById(R.id.userName111)
        val rvDesig : TextView = itemView.findViewById(R.id.userDesig)
    }
}
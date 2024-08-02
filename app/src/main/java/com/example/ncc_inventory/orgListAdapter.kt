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

class orgListAdapter(val context: Context, val adapterItems : List<companies>) : RecyclerView.Adapter<orgListAdapter.ViewHolderClass>()  {

    private var filteredUsers: List<companies> = adapterItems
    private val REQUEST_CODE = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.org_list_adapter,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
       return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentUser = filteredUsers[position]
        holder.rvName.text = currentUser.companyName
        holder.rvId.text = currentUser.companyId
        holder.rvContact.text = currentUser.contact_1
        holder.itemView.setOnClickListener {
            val intent = Intent(context,adminorgprofile::class.java)
            intent.putExtra("name",currentUser.companyName)
            intent.putExtra("id",currentUser.companyId)
            intent.putExtra("pass",currentUser.password)
            intent.putExtra("c2",currentUser.contact_2)
            intent.putExtra("c1",currentUser.contact_1)
            intent.putExtra("e1",currentUser.email)
            intent.putExtra("e2",currentUser.alternativeEmail)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
            (context as Activity).startActivityForResult(intent, REQUEST_CODE)
        }
    }

    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            adapterItems
        }else{
            adapterItems.filter {
                it.companyName.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || it.companyId.lowercase(
                    Locale.ROOT).contains(query.lowercase(Locale.ROOT))
            }
        }
        notifyDataSetChanged()
    }


    class ViewHolderClass(itemView : View) :  RecyclerView.ViewHolder(itemView) {
        val rvId : TextView = itemView.findViewById(R.id.orgId111)
        val rvName : TextView = itemView.findViewById(R.id.orgName111)
        val rvContact : TextView = itemView.findViewById(R.id.orgContact)

    }
}
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

class moderatorAdapter(val context: Context , val adapterItems : List<moderators>) : RecyclerView.Adapter<moderatorAdapter.ViewHolderClass>() {

    private var filteredUsers: List<moderators> = adapterItems
    private val REQUEST_CODE = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_lisr_adapter,parent,false)
       return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentUser = filteredUsers[position]
        holder.rvName.text = currentUser.moderatorName
        holder.rvId.text = currentUser.moderatorId
        holder.rvDesig.text = currentUser.designation
        holder.itemView.setOnClickListener {
            val intent = Intent(context,adminModeratorProfile::class.java)
            intent.putExtra("name",currentUser.moderatorName)
            intent.putExtra("id",currentUser.moderatorId)
            intent.putExtra("pass",currentUser.password)
            intent.putExtra("desig",currentUser.designation)
            intent.putExtra("section",currentUser.section)
            intent.putExtra("appt",currentUser.appointment)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
            (context as Activity).startActivityForResult(intent, REQUEST_CODE)        }
    }

    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            adapterItems
        }else{
            adapterItems.filter {
                it.moderatorName.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || it.moderatorId.lowercase(
                    Locale.ROOT).contains(query.lowercase(Locale.ROOT))
            }
        }
        notifyDataSetChanged()
    }

    class ViewHolderClass (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val rvId : TextView = itemView.findViewById(R.id.userId111)
        val rvName : TextView = itemView.findViewById(R.id.userName111)
        val rvDesig : TextView = itemView.findViewById(R.id.userDesig)
    }


}
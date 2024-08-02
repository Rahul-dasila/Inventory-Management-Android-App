package com.example.ncc_inventory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class ticketRaisedAdapter(val context: Context, val adapterItems: List<tickets>) :
    RecyclerView.Adapter<ticketRaisedAdapter.ViewHolderClass>() {

    private var filteredUsers: List<tickets> = adapterItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.raised_item_mode, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
       return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = filteredUsers[position]
        holder.ticketId.text = currentItem.ticketId
        holder.issueType.text = currentItem.issueType
        holder.issuedBy.text = currentItem.issuedBy
        holder.message.text = currentItem.message
        holder.productId.text = currentItem.productId
        holder.createdAt.text = currentItem.createdAt
        holder.status.text = currentItem.status
        if(holder.status.text.trim() == "PENDING"){
            holder.btn.text = "UNDER REVIEW"
        }else if(holder.status.text.trim() == "UNDER REVIEW"){
            holder.btn.text = "RESOLVE"
        }else{
            holder.btn.visibility = View.INVISIBLE
        }
        holder.btn.setOnClickListener {
            if(holder.btn.text == "UNDER REVIEW"){
                holder.btn.startAnimation(AnimationUtils.loadAnimation(context,R.anim.click))
                val retrofit = rFit.retrofit!!
                val request = updateticketreq(currentItem.ticketId,"UNDER REVIEW")
                val service = retrofit.create(updateTicketService::class.java)
                service.updatetct(request).enqueue(object : Callback<updateticketResponse>{
                    override fun onResponse(
                        call: Call<updateticketResponse>,
                        response: Response<updateticketResponse>
                    ) {
                        if(response.isSuccessful){
                            val respo = response.body()
                            if(respo?.success==true){
                                holder.status.text = "UNDER REVIEW"
                                holder.btn.text = "RESOLVE"
                                Toast.makeText(context,"Ticket Under Review",Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(context,"Response Failed",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<updateticketResponse>, t: Throwable) {
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                    }

                })
            }else if (holder.btn.text == "RESOLVE"){
                holder.btn.startAnimation(AnimationUtils.loadAnimation(context,R.anim.click))
                val retrofit = rFit.retrofit!!
                val request = updateticketreq(currentItem.ticketId,"RESOLVED")
                val service = retrofit.create(updateTicketService::class.java)
                service.updatetct(request).enqueue(object : Callback<updateticketResponse>{
                    override fun onResponse(
                        call: Call<updateticketResponse>,
                        response: Response<updateticketResponse>
                    ) {
                        if(response.isSuccessful){
                            val respo = response.body()
                            if(respo?.success==true){
                                holder.status.text = "RESOLVED"
                                holder.btn.visibility = View.INVISIBLE
                                Toast.makeText(context,"Ticket Resolved",Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(context,"Response Failed",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<updateticketResponse>, t: Throwable) {
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }

    }

    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            adapterItems
        } else {
            adapterItems.filter {
                it.issuedBy.lowercase(Locale.ROOT)
                    .contains(query.lowercase(Locale.ROOT)) || it.productId.lowercase(
                    Locale.ROOT
                ).contains(query.lowercase(Locale.ROOT)) || it.issueType.lowercase(Locale.ROOT)
                    .contains(
                        query.lowercase(
                            Locale.ROOT
                        )
                    )
            }
        }
        notifyDataSetChanged()
    }

    class ViewHolderClass (itemView : View) : RecyclerView.ViewHolder(itemView){
        val ticketId : TextView = itemView.findViewById(R.id.tctId)
        val issueType : TextView = itemView.findViewById(R.id.iType)
        val message : TextView = itemView.findViewById(R.id.msg)
        val issuedBy : TextView = itemView.findViewById(R.id.isdBy)
        val productId :TextView = itemView.findViewById(R.id.pidRT)
        val status :TextView = itemView.findViewById(R.id.Rstatus)
        val createdAt :TextView = itemView.findViewById(R.id.dtRT)
        val btn : TextView = itemView.findViewById(R.id.Btn)
    }

}
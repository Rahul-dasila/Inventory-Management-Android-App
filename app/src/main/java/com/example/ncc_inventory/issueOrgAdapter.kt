package com.example.ncc_inventory

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import java.util.Locale

class issueOrgAdapter(val context: Context, val adapterItems : List<orgDEmands>) : RecyclerView.Adapter<issueOrgAdapter.ViewHolderClass>() {
    private var filteredUsers: List<orgDEmands> = adapterItems
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.issue_org_item,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = filteredUsers[position]
        holder.createdAt.text = currentItem.createdAt
        holder.demandId.text = currentItem.demandId
        holder.userId.text = currentItem.companyId
        holder.productName.text = currentItem.productName
        holder.productType.text = currentItem.productType
        holder.productModel.text = currentItem.productModel
        holder.productBrand.text = currentItem.productBrand
        holder.additionalDetail.text = currentItem.additionalDetail
        holder.status.text = currentItem.status
        holder.productQuantity.text = currentItem.productQuantity.toString()
        holder.issueBtn.setOnClickListener {
            holder.issueBtn.startAnimation(AnimationUtils.loadAnimation(context,R.anim.click))
            val intent = Intent(context,issurOrgFilter::class.java)
            intent.putExtra("type",currentItem.productType)
            intent.putExtra("model",currentItem.productModel)
            intent.putExtra("brand",currentItem.productBrand)
            intent.putExtra("companyId",currentItem.companyId)
            intent.putExtra("demandId", currentItem.demandId)
            intent.putExtra("quantity",currentItem.productQuantity)
            context.startActivity(intent)
        }
    }

    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            adapterItems
        }else{
            adapterItems.filter {
                it.productType.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || it.productName.lowercase(
                    Locale.ROOT).contains(query.lowercase(Locale.ROOT))||it.companyId.lowercase(Locale.ROOT).contains(query.lowercase(
                    Locale.ROOT))
            }
        }
        notifyDataSetChanged()
    }
    class ViewHolderClass(itemView : View) :  RecyclerView.ViewHolder(itemView){
        val createdAt : TextView = itemView.findViewById<TextView>(R.id.IdOtimeMod)
        val demandId : TextView = itemView.findViewById<TextView>(R.id.IdmndIdMod)
        val userId :  TextView = itemView.findViewById<TextView>(R.id.cIdMod)
        val productName :  TextView = itemView.findViewById<TextView>(R.id.IpnameMod)
        val productType :  TextView = itemView.findViewById<TextView>(R.id.IpdtypeMod)
        val productModel :  TextView = itemView.findViewById<TextView>(R.id.IpdmodelMod)
        val productBrand : TextView = itemView.findViewById<TextView>(R.id.IbandMod)
        val additionalDetail : TextView = itemView.findViewById<TextView>(R.id.IadtMod)
        val productQuantity :  TextView = itemView.findViewById<TextView>(R.id.IqutMod)
        val status :  TextView = itemView.findViewById<TextView>(R.id.IpdstatusMod)
        val issueBtn : TextView = itemView.findViewById(R.id.ImyIssueBtn)
    }
}
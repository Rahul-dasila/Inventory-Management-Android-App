package com.example.ncc_inventory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class orgDemandAdapter(val context: Context, val adapterItems : List<companyDemands>): RecyclerView.Adapter<orgDemandAdapter.ViewHolderClass>(){
    private var filteredUsers: List<companyDemands> = adapterItems
    private var status = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.org_demand_item,parent,false)
        return ViewHolderClass(itemView)    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = filteredUsers[position]
        holder.createdAt.text = currentItem.createdAt
        holder.demandId.text = currentItem.demandId
        holder.companyId.text = currentItem.companyId
        holder.productName.text = currentItem.productName
        holder.productType.text = currentItem.productType
        holder.productModel.text = currentItem.productModel
        holder.productBrand.text = currentItem.productBrand
        holder.additionalDetail.text = currentItem.additionalDetail
        holder.status.text = currentItem.status
        holder.productQuantity.text = currentItem.productQuantity.toString()
        val click = AnimationUtils.loadAnimation(context,R.anim.click)
        holder.accept.setOnClickListener {
            holder.accept.startAnimation(click)
            val demandAcptOrRjct = demandAcptOrRjct(currentItem.demandId,"APPROVED")
            UpdateStatus(demandAcptOrRjct,holder.status)
            vib(holder.acptLay,status)
        }
        holder.reject.setOnClickListener {
            holder.accept.startAnimation(click)
            val demandAcptOrRjct = demandAcptOrRjct(currentItem.demandId,"REJECTED")
            UpdateStatus(demandAcptOrRjct,holder.status)
            vib(holder.acptLay,status)
        }

        vib(holder.acptLay,currentItem.status)
    }

    fun UpdateStatus(demandAcptOrRjct: demandAcptOrRjct,tv : TextView){
        val retroFit = rFit.retrofit!!
        val service = retroFit.create(demandAcceptOrRejectService::class.java)
        var status : String =  ""
        service.updateStatusOrg(demandAcptOrRjct).enqueue(object :
            Callback<managerDemandAcceptOrrejectResponse> {
            override fun onResponse(
                call: Call<managerDemandAcceptOrrejectResponse>,
                response: Response<managerDemandAcceptOrrejectResponse>
            ) {
                if(response.isSuccessful){
                    val respo = response.body()
                    if(respo?.success==true){
                        if(respo.status=="APPROVED"){
                            Toast.makeText(context,"APPROVED", Toast.LENGTH_SHORT).show()
                            status = respo.status
                            tv.text = status
                        }else if (respo.status == "REJECTED"){
                            Toast.makeText(context,"REJECTED", Toast.LENGTH_SHORT).show()
                            status = respo.status
                            tv.text = status
                        }
                    }
                }
            }

            override fun onFailure(call: Call<managerDemandAcceptOrrejectResponse>, t: Throwable) {
                Toast.makeText(context,"Some error occurred", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun vib(LL : LinearLayout,status: String){
        if(status=="PENDING"||status=="Pending"){
            LL.visibility  = View.VISIBLE
        }
        else{
            LL.visibility = View.INVISIBLE
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

    class ViewHolderClass (itemView: View) : RecyclerView.ViewHolder(itemView){
        val createdAt : TextView = itemView.findViewById<TextView>(R.id.oDT)
        val demandId : TextView = itemView.findViewById<TextView>(R.id.OdmndId)
        val companyId : TextView = itemView.findViewById<TextView>(R.id.OId)
        val productName : TextView = itemView.findViewById<TextView>(R.id.opname)
        val productType : TextView = itemView.findViewById<TextView>(R.id.opdtype)
        val productModel : TextView = itemView.findViewById<TextView>(R.id.opdmodel)
        val productBrand : TextView = itemView.findViewById<TextView>(R.id.oband)
        val additionalDetail : TextView = itemView.findViewById<TextView>(R.id.oadt)
        val productQuantity : TextView = itemView.findViewById<TextView>(R.id.oqut)
        val status : TextView = itemView.findViewById<TextView>(R.id.opdstatus)
        val accept : TextView = itemView.findViewById(R.id.oAccept)
        val reject : TextView = itemView.findViewById(R.id.oReject)
        val acptLay : LinearLayout = itemView.findViewById(R.id.acptLayout2)
    }

}
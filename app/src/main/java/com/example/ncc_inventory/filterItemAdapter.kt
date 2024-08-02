package com.example.ncc_inventory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import java.util.Locale


class filterItem_adapter(
    val context: Context,
    val adapterItems: List<filteredProducts>,
    val retrofit: Retrofit,
    val demandId: String,
    val userId: String,
    val onclick : ()->Unit,
) : RecyclerView.Adapter<filterItem_adapter.ViewHolderClass>() {

    val newList: MutableList<filteredProducts> = adapterItems.toMutableList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = newList[position]
        holder.productId.text = currentItem.productId
        holder.productName.text = currentItem.productName
        holder.productType.text = currentItem.productType
        holder.productModel.text = currentItem.productModel
        holder.productBrand.text = currentItem.productBrand
        holder.issueBtn.setOnClickListener {
            holder.issueBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click))
            val request = issue_btn_request(currentItem.productId, userId, demandId)
            val service = retrofit.create(Issue_Btn_service::class.java)
            service.assign(request).enqueue(object : Callback<issue_btn_response> {
                override fun onResponse(
                    call: Call<issue_btn_response>,
                    response: Response<issue_btn_response>
                ) {
                    if (response.isSuccessful) {
                        val respo = response.body()
                        if (respo?.success == true) {
                            newList.removeAt(holder.adapterPosition)
                            notifyItemRemoved(holder.adapterPosition)
                            Toast.makeText(context, "Product Assigned", Toast.LENGTH_SHORT).show()
                            if (newList.isEmpty()){
                                onclick()
                            }
                        }


                    } else {
                        Toast.makeText(context, "Response Unsuccessful", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<issue_btn_response>, t: Throwable) {
                    Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productId: TextView = itemView.findViewById(R.id.pFil)
        val productName: TextView = itemView.findViewById<TextView>(R.id.pnameFil)
        val productType: TextView = itemView.findViewById<TextView>(R.id.pdtypeFil)
        val productModel: TextView = itemView.findViewById<TextView>(R.id.pdmodelFil)
        val productBrand: TextView = itemView.findViewById<TextView>(R.id.bandFil)
        val issueBtn: TextView = itemView.findViewById(R.id.myAssignBtn)
    }
}
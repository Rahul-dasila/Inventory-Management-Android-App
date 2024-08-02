package com.example.ncc_inventory

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class receivedDemandAdapter(
    val context: Context,
    val adapterItems: List<formattedDemands>,
    val id: String
) :
    RecyclerView.Adapter<receivedDemandAdapter.ViewHolderClass>() {
    private var filteredUsers: List<formattedDemands> = adapterItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.received_itemuser, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = filteredUsers[position]
        holder.date.text = currentItem.updatedAt
        holder.productName.text = currentItem.productName
        holder.productType.text = currentItem.productType
        holder.productModel.text = currentItem.productModel
        holder.productBrand.text = currentItem.productBrand
        holder.status.text = "Issued"
        if (currentItem.ticketStatus == "RAISED") {
            holder.raise.text = "RAISED"
        }

        holder.raise.setOnClickListener {
            if (holder.raise.text != "RAISED") {
                holder.raise.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click))
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.raise_ticket)
                val items = mutableListOf("Repair", "Replace")
                var spinner = dialog.findViewById<Spinner>(R.id.spinnerR)
                var des = dialog.findViewById<EditText>(R.id.desc)
                var arr = ArrayAdapter(context, android.R.layout.simple_spinner_item, items)
                arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = arr

                var selectedItem = ""
                spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        selectedItem = parent.getItemAtPosition(position).toString()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                })

                var btn = dialog.findViewById<TextView>(R.id.rseBtn)
                btn.setOnClickListener {
                    var txt = des.text.toString()
                    if (txt.isEmpty()) {
                        txt = "None"
                    }
                    val retrofit = rFit.retrofit!!
                    val request =
                        raiseReq(generateId(), selectedItem, txt, id, currentItem.productId)
                    val service = retrofit.create(RaiseService::class.java)
                    service.raise(request).enqueue(object : Callback<raiseResponse> {
                        override fun onResponse(
                            call: Call<raiseResponse>,
                            response: Response<raiseResponse>
                        ) {
                            if (response.isSuccessful) {
                                val respo = response.body()
                                if (respo?.success == true) {
                                    Toast.makeText(context, "Ticket Raised", Toast.LENGTH_SHORT)
                                        .show()
                                    holder.raise.text = "RAISED"
                                    dialog.dismiss()
                                }
                            } else {
                                Toast.makeText(context, "Response Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<raiseResponse>, t: Throwable) {
                            Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
                        }

                    })

                }

                dialog.show()

            }else{
                Toast.makeText(context,"Ticket already raised for this item",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            adapterItems
        } else {
            adapterItems.filter {
                it.productType.lowercase(Locale.ROOT)
                    .contains(query.lowercase(Locale.ROOT)) || it.productName.lowercase(
                    Locale.ROOT
                ).contains(query.lowercase(Locale.ROOT)) || it.productType.lowercase(Locale.ROOT)
                    .contains(
                        query.lowercase(
                            Locale.ROOT
                        )
                    )
            }
        }
        notifyDataSetChanged()
    }

    private fun generateId(): String {
        val firstTwo = kotlin.random.Random.nextInt(10, 99).toString()
        val middle1 = kotlin.random.Random.nextInt('A'.code, 'Z'.code + 1).toChar()
        val middle2 = kotlin.random.Random.nextInt('A'.code, 'Z'.code + 1).toChar()
        val middle3 = kotlin.random.Random.nextInt('A'.code, 'Z'.code + 1).toChar()
        val lastTwo = kotlin.random.Random.nextInt(10, 99).toString()
        val two = kotlin.random.Random.nextInt(10, 99).toString()
        val middle4 = kotlin.random.Random.nextInt('A'.code, 'Z'.code + 1).toChar()
        val ID = firstTwo + middle1 + middle2 + two + middle3 + middle4 + lastTwo
        return ID
    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.ctAt)
        val productName: TextView = itemView.findViewById<TextView>(R.id.Rname)
        val productType: TextView = itemView.findViewById<TextView>(R.id.Rtype)
        val productModel: TextView = itemView.findViewById<TextView>(R.id.Rmodel)
        val productBrand: TextView = itemView.findViewById<TextView>(R.id.Rband)
        val status: TextView = itemView.findViewById<TextView>(R.id.Rstatus)
        val raise: TextView = itemView.findViewById(R.id.raiseBtn)
    }
}
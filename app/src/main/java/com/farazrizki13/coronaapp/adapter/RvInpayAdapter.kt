package com.farazrizki13.coronaapp.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.farazrizki13.coronaapp.R
import com.farazrizki13.coronaapp.model.InpayModel
import kotlinx.android.synthetic.main.rv_bank_inpay.view.*

class RvInpayAdapter(private val item : List<InpayModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class RvInpayViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        fun bind(inpayModel: InpayModel) {
            with(itemView) {
                val title = inpayModel.title
                val code = inpayModel.code

                itemView.card_bank_title.text = title

                itemView.setOnClickListener {
                    itemView.card_pay.animate().alpha(.8F).setDuration(300).start()
                    val handler= Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        itemView.card_pay.animate().alpha(1F).setDuration(300).start()
                        Toast.makeText(context, code, Toast.LENGTH_SHORT).show()
                    }, 600)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RvInpayViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_bank_inpay, parent, false)
        )
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RvInpayViewHolder -> {
                holder.bind(item[position])
            }
        }
    }

}
package com.farazrizki13.coronaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.farazrizki13.coronaapp.R
import com.farazrizki13.coronaapp.model.IndesignModel
import kotlinx.android.synthetic.main.rv_inpay_2.view.*

class RvInDesignAdapter(private val item : List<IndesignModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class RvInDesignViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        fun bind(indesignModel: IndesignModel) {
            with(itemView) {
                val img = indesignModel.img
                val title = indesignModel.title
                val code = indesignModel.code

                itemView.img_card.setImageResource(img)
                itemView.tv_card.text = title

                itemView.setOnClickListener {
                    Toast.makeText(context, code, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RvInDesignViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_inpay_2, parent, false)
        )
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RvInDesignViewHolder -> {
                holder.bind(item[position])
            }
        }
    }
}
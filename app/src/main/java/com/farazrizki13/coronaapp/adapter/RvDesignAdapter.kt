package com.farazrizki13.coronaapp.adapter

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.farazrizki13.coronaapp.BankActivity
import com.farazrizki13.coronaapp.R
import com.farazrizki13.coronaapp.model.DesignModel
import kotlinx.android.synthetic.main.rv_design_menu.view.*

class RvDesignAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var item : List<DesignModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RvDesignViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_design_menu, parent, false)
        )
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RvDesignViewHolder -> {
                holder.bind(item[position])
            }
        }
    }

    fun submitlist(designList : List<DesignModel>){
        item = designList
    }

    inner class RvDesignViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        fun bind (designModel: DesignModel) {
            with(itemView) {
                val title = designModel.title
                val subtitle = designModel.subtitle
                val code = designModel.code

                itemView.title_card.text = title
                itemView.subtitle_card.text = subtitle

                itemView.setOnClickListener {
                    itemView.animate().alpha(0.8F).setDuration(300).start()
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        itemView.animate().alpha(1F).setDuration(300).start()
                    },350)
                    when (code) {
                        "bank" -> context.startActivity(Intent(context, BankActivity::class.java))
                        else -> Toast.makeText(context, "hallo you will go to $title", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}
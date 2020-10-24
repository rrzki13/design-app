package com.farazrizki13.coronaapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.farazrizki13.coronaapp.R
import com.farazrizki13.coronaapp.adapter.RvInDesignAdapter
import com.farazrizki13.coronaapp.adapter.RvInpayAdapter
import com.farazrizki13.coronaapp.model.IndesignModel
import com.farazrizki13.coronaapp.model.InpayModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var inpayAdapter : RvInpayAdapter
    private lateinit var indesignAdapter : RvInDesignAdapter

    private val inpaySource = listOf(
        InpayModel("Inpay", "inpay"),
        InpayModel("Bpay", "bpay"),
        InpayModel("Idpay", "idpay")
    )

    private val inDesignSource = listOf(
        IndesignModel(R.drawable.char_1, "A", "Tech"),
        IndesignModel(R.drawable.char_2, "B", "Metting"),
        IndesignModel(R.drawable.char_3, "C", "Commerce"),
        IndesignModel(R.drawable.char_4, "D", "Code"),
        IndesignModel(R.drawable.char_5, "E", "Virtual game")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_inpay.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            inpayAdapter = RvInpayAdapter(inpaySource)
            adapter = inpayAdapter
        }

        rv_inpay_2.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            indesignAdapter = RvInDesignAdapter(inDesignSource)
            adapter = indesignAdapter
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
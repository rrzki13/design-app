package com.farazrizki13.coronaapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farazrizki13.coronaapp.R
class BankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bank, container, false)
        return view
    }

    companion object {
        private val TAG = BankFragment::class.qualifiedName
        @JvmStatic fun newInstance(): BankFragment {
            val fragment = BankFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
package com.farazrizki13.coronaapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.farazrizki13.coronaapp.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_saya.view.*

class SayaFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_saya, container, false)
        view.my_profile_nav.setNavigationItemSelectedListener(this)

        return view
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navigation_favourite -> Toast.makeText(activity, "nav 1", Toast.LENGTH_SHORT).show()
            R.id.navigation_user_edit -> Toast.makeText(activity, "nav 2", Toast.LENGTH_SHORT).show()
            R.id.navigation_friends -> Toast.makeText(activity, "nav 3", Toast.LENGTH_SHORT).show()
            R.id.navigation_promotion -> Toast.makeText(activity, "nav 4", Toast.LENGTH_SHORT).show()
            R.id.navigation_cog -> Toast.makeText(activity, "nav 5", Toast.LENGTH_SHORT).show()
            R.id.navigation_logout -> Toast.makeText(activity, "nav logout", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    companion object {
        fun newInstance(): SayaFragment {
            val fragment = SayaFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
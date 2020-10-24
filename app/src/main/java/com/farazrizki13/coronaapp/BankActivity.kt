package com.farazrizki13.coronaapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.farazrizki13.coronaapp.fragment.BankFragment
import com.farazrizki13.coronaapp.fragment.HomeFragment
import com.farazrizki13.coronaapp.fragment.SayaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_bank.*

class BankActivity : AppCompatActivity() {

    private val mOnNavigationItemReselectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item ->
        when(item.itemId) {
            R.id.navigation_home -> {
                val fragment = HomeFragment.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bank -> {
                val fragment = BankFragment.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_saya -> {
                val fragment = SayaFragment.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                Toast.makeText(this, "hai", Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemReselectedListener)
        val fragment = HomeFragment.newInstance()
        addFragment(fragment)
    }



    @SuppressLint("PrivateResource")
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}
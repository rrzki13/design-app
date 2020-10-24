package com.farazrizki13.coronaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.farazrizki13.coronaapp.adapter.RvDesignAdapter
import com.farazrizki13.coronaapp.api.ApiClient
import com.farazrizki13.coronaapp.model.CovidModel
import com.farazrizki13.coronaapp.source.DesignSource
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var designAdapter : RvDesignAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setData()

        setDesign()
        addDataSet()
    }

    private fun addDataSet(){
        val data = DesignSource.createDataSet()
        designAdapter.submitlist(data)
    }

    private fun setDesign() {
        rv_design.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            designAdapter = RvDesignAdapter()
            adapter = designAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    private fun setData() {
        ApiClient.instances.getData().enqueue(object : Callback<ArrayList<CovidModel>>{
            override fun onFailure(call: Call<ArrayList<CovidModel>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ArrayList<CovidModel>>,
                response: Response<ArrayList<CovidModel>>
            ) {
                val positif = response.body()?.get(0)?.positif
                val sembuh = response.body()?.get(0)?.sembuh
                val meniggal = response.body()?.get(0)?.meninggal
                val dirawat = response.body()?.get(0)?.dirawat

                covid_positif.text = positif
                covid_meninggal.text = meniggal
                covid_sembuh.text = sembuh
                covid_dirawat.text = dirawat
            }

        })
    }
}
package com.farazrizki13.coronaapp.source

import com.farazrizki13.coronaapp.model.DesignModel

class DesignSource {
    companion object {
        fun createDataSet() : ArrayList<DesignModel> {
            val list = ArrayList<DesignModel>()
            list.add(
                DesignModel(
                    "Covid 19 Application",
                    "Data corona di indonesia",
                    "covid"
                )
            )

            list.add(
                DesignModel(
                    "Bank Application",
                    "Aplikasi Mobile banking",
                    "bank"
                )
            )

            return list
        }
    }
}
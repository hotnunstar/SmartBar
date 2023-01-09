package com.ipca.smartbar.client.historic

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ipca.smartbar.R


class ClientHistoricDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_historic_detail)

        val detailtvRequestHist = intent.getStringExtra("request")

        findViewById<TextView>(R.id.tvRequestHistDetail).text = detailtvRequestHist
    }
}
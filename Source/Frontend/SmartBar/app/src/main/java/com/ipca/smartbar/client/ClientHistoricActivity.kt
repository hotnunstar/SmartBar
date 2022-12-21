package com.ipca.smartbar.client

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ipca.smartbar.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ClientHistoricActivity : AppCompatActivity() {

    val historic = arrayListOf<Historic>()

    val adapter = HistoricAdapter()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_historic)

        val stringDate = "18-07-2019 16:20"
        val dateTime = LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        historic.add(Historic("100", dateTime, 10.00,1))

        val listViewHistoric = findViewById<ListView>(R.id.listViewHistoric)
        listViewHistoric.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_client, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId) {
            R.id.action_profile_client -> {
                val intent = Intent(this@ClientHistoricActivity, ClientProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_products_client -> {

                true
            }
            R.id.action_cart_client -> {
                val intent = Intent(this@ClientHistoricActivity, ClientCartActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_client -> {
                val intent = Intent(this@ClientHistoricActivity, ClientHistoricActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_notifications_client -> {
                val intent = Intent(this@ClientHistoricActivity, ClientNotificationsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class HistoricAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return historic.size
        }

        override fun getItem(position: Int): Any {
            return historic[position]
        }

        override fun getItemId(position: Int): Long {
            return 0L
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_historic, parent, false)
            val textViewPedidoHist = rootView.findViewById<TextView>(R.id.textViewPedidoHist)
            val textViewData = rootView.findViewById<TextView>(R.id.textViewData)
            val textViewTotalPrice = rootView.findViewById<TextView>(R.id.textViewTotalPrice)

            textViewPedidoHist.text = historic[position].idPedido
            textViewData.text = historic[position].data.toString()
            textViewTotalPrice.text = historic[position].valor.toString()

            return rootView
        }
    }
}


package com.ipca.smartbar.client.historic

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.client.notifications.ClientNotificationsActivity
import com.ipca.smartbar.client.products.ClientProductsActivity
import com.ipca.smartbar.client.cart.ClientCartActivity
import com.ipca.smartbar.client.profile.ClientProfileActivity
import com.ipca.smartbar.databinding.ActivityClientHistoricBinding
import com.ipca.smartbar.getToken


class ClientHistoricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientHistoricBinding

    val adapter = CHAdapter()
    var historic = arrayListOf<ClientHistoricModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientHistoricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = getToken()

        CHBackendRequests.getHistoric(lifecycleScope, token){
            historic = it
            adapter.notifyDataSetChanged()
        }
        binding.listViewClientHistoric.adapter = adapter
    }

    inner class CHAdapter : BaseAdapter() {
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
            val rowView = layoutInflater.inflate(R.layout.row_historic, parent, false)
            val tvRequestHist = rowView.findViewById<TextView>(R.id.tvRequestHist)
            val tvTotalPriceHist = rowView.findViewById<TextView>(R.id.tvTotalPriceHist)
            val tvDateRequestHist = rowView.findViewById<TextView>(R.id.tvDateRequestHist)

            val hist = historic[position]
            tvRequestHist.text = hist.idRequest
            tvTotalPriceHist.text = hist.totalPrice.toString()
            tvDateRequestHist.text = hist.dateRequest

            tvDateRequestHist.text = (tvDateRequestHist.text as String).replace("T", " ")

            return rowView
        }
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
                val intent = Intent(this@ClientHistoricActivity, ClientProductsActivity::class.java)
                startActivity(intent)
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

}


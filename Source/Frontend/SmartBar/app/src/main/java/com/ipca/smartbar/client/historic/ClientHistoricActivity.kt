package com.ipca.smartbar.client.historic

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.ipca.smartbar.R
import com.ipca.smartbar.client.notifications.ClientNotificationsActivity
import com.ipca.smartbar.client.products.ClientProductsActivity
import com.ipca.smartbar.client.cart.ClientCartActivity
import com.ipca.smartbar.client.profile.ClientProfileActivity
import com.ipca.smartbar.databinding.ActivityClientHistoricBinding
import com.ipca.smartbar.getToken


class ClientHistoricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientHistoricBinding

    private val adapter = CHAdapter()
    private var historic = arrayListOf<ClientHistoricModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientHistoricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = getToken()

        CHBackendRequests.getHistoric(lifecycleScope, token){
            historic = it

            if (it.size == 0)
            {
                Toast.makeText(this,"Não Existem Pedidos", Toast.LENGTH_LONG).show()
            }

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
            tvRequestHist.append("${hist.idRequest}")
            tvTotalPriceHist.append("${hist.totalPrice}" + "€")
            tvDateRequestHist.append("${hist.dateRequest}")

            rowView.setOnClickListener{
                val gson = Gson()
                val intent = Intent(this@ClientHistoricActivity, ClientHistoricDetailActivity::class.java)

                intent.putExtra("identifier", gson.toJson(hist))
                Log.e("cebolas", gson.toJson(hist))
                startActivity(intent)
            }
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


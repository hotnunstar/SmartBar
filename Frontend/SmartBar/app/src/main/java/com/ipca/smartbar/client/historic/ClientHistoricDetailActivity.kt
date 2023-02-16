package com.ipca.smartbar.client.historic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.products.BarProductsModel
import com.ipca.smartbar.client.cart.ClientCartActivity
import com.ipca.smartbar.client.notifications.ClientNotificationsActivity
import com.ipca.smartbar.client.products.ClientProductsActivity
import com.ipca.smartbar.client.profile.ClientProfileActivity
import com.ipca.smartbar.databinding.ActivityClientHistoricDetailBinding
import com.ipca.smartbar.getToken


class ClientHistoricDetailActivity: AppCompatActivity(){

    private lateinit var binding: ActivityClientHistoricDetailBinding

    private val adapter = CHDetailAdapter()
    var products: ArrayList<BarProductsModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = Gson()
        val hist = gson.fromJson(intent.getStringExtra("identifier"), ClientHistoricModel::class.java)

        binding = ActivityClientHistoricDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = getToken()
        CHBackendRequests.getProductByID(lifecycleScope, token, hist.productAndQuantity!!){
            products = it
            adapter.notifyDataSetChanged()

        }
        binding.lVClientHistoricProdDetail.adapter = adapter
        val tvRequestHistDetail = binding.tvRequestHistDetail
        val tvDateRequestHistDetail = binding.tvDateRequestHistDetail
        val tvHorasHistDetail = binding.tvHorasHistDetail
        val tvTotalPriceHistDetail = binding.tvTotalPriceHistDetail
        val tvIdBarHistDetail = binding.tvIdBarHistDetail

        tvRequestHistDetail.append("${hist.idRequest}")
        tvDateRequestHistDetail.append("${hist.dateRequest}")
        tvHorasHistDetail.append("${hist.horas}")
        tvTotalPriceHistDetail.append("${hist.totalPrice}")
        tvIdBarHistDetail.append("${hist.idBar}")

    }

    inner class CHDetailAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return products.size
        }

        override fun getItem(position: Int): Any {
            return products[position]
        }

        override fun getItemId(position: Int): Long {
            return 0L
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_request_products_line, parent, false)

            val gson = Gson()
            val hist = gson.fromJson(intent.getStringExtra("identifier"), ClientHistoricModel::class.java)

            val textViewProductName = rowView.findViewById<TextView>(R.id.textViewProductLineName)
            val textViewProductQuantity = rowView.findViewById<TextView>(R.id.textViewProductLineQuantity)
            val textViewTotalPrice = rowView.findViewById<TextView>(R.id.textViewProductLinePrice)
            val product = products[position]

            val productTotalPrice = hist.productAndQuantity!![position].quantity * product.price
            textViewProductName.append(" ${product.name}")
            textViewProductQuantity.append(" ${hist.productAndQuantity?.get(position)!!.quantity}")
            textViewTotalPrice.text = "${productTotalPrice}€"

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
                val intent = Intent(this@ClientHistoricDetailActivity, ClientProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_products_client -> {
                val intent = Intent(this@ClientHistoricDetailActivity, ClientProductsActivity::class.java)
                startActivity(intent)
                true

            }
            R.id.action_cart_client -> {
                val intent = Intent(this@ClientHistoricDetailActivity, ClientCartActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_client -> {
                val intent = Intent(this@ClientHistoricDetailActivity, ClientHistoricActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_notifications_client -> {
                val intent = Intent(this@ClientHistoricDetailActivity, ClientNotificationsActivity::class.java)
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
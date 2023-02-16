package com.ipca.smartbar.bar.historic

import android.content.Intent
import android.os.Bundle
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
import com.ipca.smartbar.bar.products.BarProductsActivity
import com.ipca.smartbar.bar.products.BarProductsModel
import com.ipca.smartbar.bar.profile.BarProfileActivity
import com.ipca.smartbar.databinding.ActivityBarHistoricDetailBinding
import com.ipca.smartbar.getToken


class BarHistoricDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityBarHistoricDetailBinding

    private val adapter = BHDetailAdapter()
    var products: ArrayList<BarProductsModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = Gson()
        val hist = gson.fromJson(intent.getStringExtra("identifier"), BarHistoricModel::class.java)

        binding = ActivityBarHistoricDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = getToken()
        BHBackendRequests.getProductByID(lifecycleScope, token, hist.productAndQuantity!!){
            products = it
            adapter.notifyDataSetChanged()

        }
        binding.lVBarHistoricProdDetail.adapter = adapter
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

    inner class BHDetailAdapter : BaseAdapter() {
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
            val hist = gson.fromJson(intent.getStringExtra("identifier"), BarHistoricModel::class.java)

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
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId) {
            R.id.action_profile_staff -> {
                val intent = Intent(this@BarHistoricDetailActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_product_settings_staff -> {
                val intent = Intent(this@BarHistoricDetailActivity, BarProductsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_requests_staff -> {
                val intent = Intent(this@BarHistoricDetailActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_staff -> {
                val intent = Intent(this@BarHistoricDetailActivity, BarHistoricActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
        return super.onOptionsItemSelected(item)
    }
}


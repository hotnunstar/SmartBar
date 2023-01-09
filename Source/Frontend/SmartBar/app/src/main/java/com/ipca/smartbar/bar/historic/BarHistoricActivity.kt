package com.ipca.smartbar.bar.historic

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
import com.ipca.smartbar.bar.products.BarProductsActivity
import com.ipca.smartbar.bar.profile.BarProfileActivity
import com.ipca.smartbar.databinding.ActivityBarHistoricBinding
import com.ipca.smartbar.getToken

class BarHistoricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarHistoricBinding

    val adapter = BHAdapter()
    var historic = arrayListOf<BarHistoricModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBarHistoricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = getToken()

        BHBackendRequests.getAllHistoric(lifecycleScope, token){
            historic = it
            adapter.notifyDataSetChanged()
        }
        binding.listViewBarHistoric.adapter = adapter
    }

    inner class BHAdapter : BaseAdapter() {
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
            val rowView = layoutInflater.inflate(R.layout.row_historic_bar, parent, false)
            val tvRequestHist = rowView.findViewById<TextView>(R.id.tvRequestHistBar)
            val tvTotalPriceHist = rowView.findViewById<TextView>(R.id.tvTotalPriceHistBar)
            val tvDateRequestHist = rowView.findViewById<TextView>(R.id.tvDateRequestHistBar)

            val hist = historic[position]
            tvRequestHist.text = hist.idRequest
            tvTotalPriceHist.text = hist.totalPrice.toString()
            tvDateRequestHist.text = hist.dateRequest

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
                val intent = Intent(this@BarHistoricActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_product_settings_staff -> {
                val intent = Intent(this@BarHistoricActivity, BarProductsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_requests_staff -> {
                val intent = Intent(this@BarHistoricActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_staff -> {
                val intent = Intent(this@BarHistoricActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
        return super.onOptionsItemSelected(item)
    }
}
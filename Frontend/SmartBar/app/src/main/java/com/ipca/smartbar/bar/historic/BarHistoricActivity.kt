package com.ipca.smartbar.bar.historic

import android.app.DatePickerDialog
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
import com.ipca.smartbar.bar.products.BarProductsActivity
import com.ipca.smartbar.bar.profile.BarProfileActivity
import com.ipca.smartbar.bar.requests.BarRequestsActivity
import com.ipca.smartbar.databinding.ActivityBarHistoricBinding
import com.ipca.smartbar.getToken
import kotlinx.android.synthetic.main.activity_bar_historic.*
import kotlinx.android.synthetic.main.activity_bar_requests.*
import java.util.*

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
        var data: String = ""

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btnDate.setOnClickListener(){
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ _, mYear, mMonth, mDay ->
                var mes = mMonth.toString()
                var dia = mDay.toString()
                if(mMonth.toString().length == 1)
                {
                    mes = "0${mMonth+1}"
                }
                if(mDay.toString().length == 1)
                {
                    dia = "0${mDay}"
                }

                tvDateCalendar.text = "$dia/$mes/$mYear"
                data = "$dia/$mes/$mYear"

            }, year, month, day)
            dpd.show()

            btnProcura.visibility = View.VISIBLE
        }


        btnProcura.setOnClickListener() {
            BHBackendRequests.getBHByBarAndDate(lifecycleScope, token, data){
                historic = it

                if (it.size == 0)
                {
                    Toast.makeText(this,"Não Existem Pedidos Nessa Data", Toast.LENGTH_LONG).show()
                }

                adapter.notifyDataSetChanged()
            }
            binding.listViewBarHistoric.adapter = adapter


        }
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
                val intent = Intent(this@BarHistoricActivity, BarHistoricDetailActivity::class.java)

                intent.putExtra("identifier", gson.toJson(hist))
                Log.e("cebolas", gson.toJson(hist))
                startActivity(intent)
            }

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
                val intent = Intent(this@BarHistoricActivity, BarRequestsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_staff -> {
                val intent = Intent(this@BarHistoricActivity, BarHistoricActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
        return super.onOptionsItemSelected(item)
    }
}
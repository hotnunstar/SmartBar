package com.ipca.smartbar.client

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import com.ipca.smartbar.R
import com.ipca.smartbar.client.models.Pedido
import java.time.LocalDateTime

class ClientCartActivity : AppCompatActivity() {

    private val spinnerOptionBar = arrayOf("Bar 1", "Bar 2")

    @RequiresApi(Build.VERSION_CODES.O)
    private val pedido = Pedido("10", "10", LocalDateTime.now(), 10.00, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_cart)

        var spinnerBar = findViewById<Spinner>(R.id.spinnerChooseBar)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerOptionBar)
        spinnerBar.adapter = arrayAdapter

        spinnerBar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                pedido.bar = spinnerOptionBar[position]
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onNothingSelected(parent: AdapterView<*>?) {
                pedido.bar = spinnerOptionBar[0]
            }
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
                val intent = Intent(this@ClientCartActivity, ClientProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_products_client -> {
                val intent = Intent(this@ClientCartActivity, ClientProductsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_cart_client -> {
                val intent = Intent(this@ClientCartActivity, ClientCartActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_client -> {
                val intent = Intent(this@ClientCartActivity, ClientHistoricActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_notifications_client -> {
                val intent = Intent(this@ClientCartActivity, ClientNotificationsActivity::class.java)
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
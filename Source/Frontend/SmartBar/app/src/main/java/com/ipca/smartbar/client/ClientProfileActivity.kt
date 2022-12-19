package com.ipca.smartbar.client

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.BaseAdapter
import android.widget.Button
import com.ipca.smartbar.R
import com.ipca.smartbar.generic.LoginActivity

class ClientProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_profile)

        val buttonTS = findViewById(R.id.buttonTerminarSessao) as Button
        buttonTS.setOnClickListener {
            val intent = Intent(this@ClientProfileActivity, LoginActivity::class.java)
            startActivity(intent)
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
                val intent = Intent(this@ClientProfileActivity, ClientProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_products_client -> {
                TODO("CRIAR FRAGMENTS PARA OS DIFERENTES TIPOS DE PRODUTOS")
                true
            }
            R.id.action_cart_client -> {
                val intent = Intent(this@ClientProfileActivity, ClientCartActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_client -> {
                val intent = Intent(this@ClientProfileActivity, ClientHistoricActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_notifications_client -> {
                val intent = Intent(this@ClientProfileActivity, ClientNotificationsActivity::class.java)
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
package com.ipca.smartbar.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipca.smartbar.R

class ClientCartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_cart)
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
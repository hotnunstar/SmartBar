package com.ipca.smartbar.client.profile

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.ipca.smartbar.R
import com.ipca.smartbar.client.cart.ClientCartActivity
import com.ipca.smartbar.client.historic.ClientHistoricActivity
import com.ipca.smartbar.client.notifications.ClientNotificationsActivity
import com.ipca.smartbar.client.products.ClientProductsActivity
import com.ipca.smartbar.deleteToken
import com.ipca.smartbar.generic.LoginActivity

class ClientProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_profile)

        val textViewClientBalance = findViewById<TextView>(R.id.textViewClientBalance)
        val textViewClientEmail = findViewById<TextView>(R.id.textViewClientEmail)
        val textViewClientName = findViewById<TextView>(R.id.textViewClientName)

        val onChangePasswordPressed: ((View)->Unit) = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://portal.ipca.pt/Intranet/ResetPassword/ResetUserPassword"))
            startActivity(intent)
        }
        val textViewChangePassword = findViewById<TextView>(R.id.textViewChangePassword)
        textViewChangePassword.setOnClickListener(onChangePasswordPressed)


        val onButtonLogoutPressed: ((View) -> Unit) = {
            deleteToken()

            val intent = Intent(this@ClientProfileActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        val buttonLogout = findViewById<Button>(R.id.buttonTerminarSessao)
        buttonLogout.setOnClickListener(onButtonLogoutPressed)
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
                val intent = Intent(this@ClientProfileActivity, ClientProductsActivity::class.java)
                startActivity(intent)
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

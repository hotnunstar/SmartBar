package com.ipca.smartbar.bar.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.historic.BarHistoricActivity
import com.ipca.smartbar.bar.products.BarProductsActivity
import com.ipca.smartbar.bar.requests.BarRequestsActivity
import com.ipca.smartbar.deleteToken
import com.ipca.smartbar.generic.LoginActivity
import com.ipca.smartbar.getToken

class BarProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_profile)

        val token = getToken()

        val textViewBarID = findViewById<TextView>(R.id.textViewBarId)
        val textViewBarDescription = findViewById<TextView>(R.id.textViewBarDescription)
        val textViewBarEmail = findViewById<TextView>(R.id.textViewBarEmail)
        val progressBarCollaboratorProfile = findViewById<ProgressBar>(R.id.progressBarBarProfile)
        val viewProgressBarCollaboratorProfile = findViewById<View>(R.id.viewProgressBarBarProfile)
        viewProgressBarCollaboratorProfile.visibility = View.VISIBLE
        progressBarCollaboratorProfile.visibility = View.VISIBLE

        BarProfileRequests.getBarProfile(lifecycleScope, token) {
            if (it.id.isNotEmpty()) {
                textViewBarID.text = it.id
                textViewBarEmail.text = it.email
                textViewBarDescription.text = it.description
                viewProgressBarCollaboratorProfile.visibility = View.GONE
                progressBarCollaboratorProfile.visibility = View.GONE
            } else {
                viewProgressBarCollaboratorProfile.visibility = View.GONE
                progressBarCollaboratorProfile.visibility = View.GONE
                Toast.makeText(this, "Erro de conexão", Toast.LENGTH_LONG).show()
                deleteToken()
                val intent = Intent(this@BarProfileActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        val onButtonLogoutPressed: ((View) -> Unit) = {
            deleteToken()
            val intent = Intent(this@BarProfileActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        val buttonLogout = findViewById<Button>(R.id.buttonBarLogout)
        buttonLogout.setOnClickListener(onButtonLogoutPressed)
    }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_bar, menu)
            return super.onCreateOptionsMenu(menu)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId) {
            R.id.action_profile_staff -> {
                val intent = Intent(this@BarProfileActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_product_settings_staff -> {
                val intent = Intent(this@BarProfileActivity, BarProductsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_requests_staff -> {
                val intent = Intent(this@BarProfileActivity, BarRequestsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_staff -> {
                val intent = Intent(this@BarProfileActivity, BarHistoricActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
        return super.onOptionsItemSelected(item)
    }
}
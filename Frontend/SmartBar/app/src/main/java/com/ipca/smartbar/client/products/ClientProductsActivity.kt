package com.ipca.smartbar.client.products

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipca.smartbar.R
import com.ipca.smartbar.client.cart.ClientCartActivity
import com.ipca.smartbar.client.historic.ClientHistoricActivity
import com.ipca.smartbar.client.notifications.ClientNotificationsActivity
import com.ipca.smartbar.client.profile.ClientProfileActivity
import com.ipca.smartbar.databinding.ActivityClientMainBinding
import com.ipca.smartbar.getToken

class ClientProductsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityClientMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null)
        {
            val fragment = HotFoodFragment(getToken())
            supportFragmentManager.beginTransaction().
                    replace(R.id.content_frame,fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss()
        }
        setUpBottomNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_client, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId) {
            R.id.action_profile_client -> {
                val intent = Intent(this@ClientProductsActivity, ClientProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_products_client -> {
                val intent = Intent(this@ClientProductsActivity, ClientProductsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_cart_client -> {
                val intent = Intent(this@ClientProductsActivity, ClientCartActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_client -> {
                val intent = Intent(this@ClientProductsActivity, ClientHistoricActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_notifications_client -> {
                val intent = Intent(this@ClientProductsActivity, ClientNotificationsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                false
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setUpBottomNavigation()
    {
        val bottonBar = binding.bottomNavigation
        bottonBar.setOnItemSelectedListener {
            item ->
            when(item.itemId)
            {
                R.id.action_product_hot_food ->{
                    val fragment = HotFoodFragment(getToken())
                    supportFragmentManager.beginTransaction().
                    replace(R.id.content_frame,fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss()
                    true

                }
                R.id.action_products_packaged ->{
                    val fragment = PackagedFragment(getToken())
                    supportFragmentManager.beginTransaction().
                    replace(R.id.content_frame,fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss()
                    true
                }
                R.id.action_products_hot_drink -> {
                    val fragment = HotDrinkFragment(getToken())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null).commitAllowingStateLoss()
                    true
                }
                R.id.action_products_cold_drink ->{
                    val fragment = ColdDrinkFragment(getToken())
                    supportFragmentManager.beginTransaction().
                    replace(R.id.content_frame,fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss()
                    true
                }
                else -> true
            }
        }
    }
}
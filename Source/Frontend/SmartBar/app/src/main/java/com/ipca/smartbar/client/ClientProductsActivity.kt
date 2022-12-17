package com.ipca.smartbar.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipca.smartbar.R
import com.ipca.smartbar.databinding.ActivityClientMainBinding

class ClientProductsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityClientMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null)
        {
            /*val fragment = HotFoodFragment()
            supportFragmentManager.beginTransaction().
                    replace(R.id.content_frame,fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss()*/
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
                TODO("CRIAR FRAGMENTS PARA OS DIFERENTES TIPOS DE PRODUTOS")
                true
            }
            R.id.action_cart_client -> {
                val intent = Intent(this@ClientProductsActivity, ClientProfileActivity::class.java)
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
    fun setUpBottomNavigation()
    {
        val bottonBar = binding.bottomNavigation
        bottonBar.setOnItemSelectedListener {
            item ->
            when(item.itemId)
            {
                R.id.action_product_hot_food ->{
                    val fragment = HotFoodFragment()
                    supportFragmentManager.beginTransaction().
                    replace(R.id.content_frame,fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss()
                    true

                }

                R.id.action_products_packaged ->
                    //todo ir para fragment
                    true
                R.id.action_products_hot_drink ->
                    //todo ir para fragment
                    true
                R.id.action_products_cold_drink ->
                    //todo ir para fragment
                    true

                else -> true
            }
        }
    }
}
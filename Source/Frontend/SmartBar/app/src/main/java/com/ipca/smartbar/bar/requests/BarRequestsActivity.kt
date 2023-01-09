package com.ipca.smartbar.bar.requests

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.products.BarProductsActivity
import com.ipca.smartbar.bar.profile.BarProfileActivity
import com.ipca.smartbar.bar.requests.navigation.BarNewRequestsFragment
import com.ipca.smartbar.bar.requests.navigation.BarRequestsInProcessFragment
import com.ipca.smartbar.databinding.ActivityBarRequestsBinding
import com.ipca.smartbar.getToken

class BarRequestsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_requests)

        binding = ActivityBarRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = BarNewRequestsFragment(getToken())
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewBarRequests, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()

        setUpBottomNavigation()
    }

    fun setUpBottomNavigation()
    {
        val bottomBar = binding.navViewBarRequests
        bottomBar.setOnItemSelectedListener {
                item ->
            when(item.itemId)
            {
                R.id.navigationNewRequests ->{
                    val fragment = BarNewRequestsFragment(getToken())
                    supportFragmentManager.beginTransaction().
                    replace(R.id.fragmentContainerViewBarRequests,fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss()
                    true
                }
                R.id.navigationRequestsInProcess ->{
                    val fragment = BarRequestsInProcessFragment(getToken())
                    supportFragmentManager.beginTransaction().
                    replace(R.id.fragmentContainerViewBarRequests,fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss()
                    true
                }
                else -> true
            }
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
                val intent = Intent(this@BarRequestsActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_product_settings_staff -> {
                val intent = Intent(this@BarRequestsActivity, BarProductsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_requests_staff -> {
                val intent = Intent(this@BarRequestsActivity, BarRequestsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_staff -> {
                val intent = Intent(this@BarRequestsActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
        return super.onOptionsItemSelected(item)
    }
}
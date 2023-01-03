package com.ipca.smartbar.bar.products

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.products.navigation.*
import com.ipca.smartbar.bar.profile.BarProfileActivity
import com.ipca.smartbar.databinding.ActivityBarProductsBinding
import com.ipca.smartbar.getToken

class BarProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBarProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = BarMenuFragment(getToken())
        supportFragmentManager.beginTransaction().
        replace(R.id.fragmentContainerViewBarProducts,fragment).
        addToBackStack(null).
        commitAllowingStateLoss()

        setUpBottomNavigation()
    }

    fun setUpBottomNavigation()
    {
        val bottomBar = binding.navViewBarProducts
        bottomBar.setOnItemSelectedListener {
                item ->
            when(item.itemId)
            {
                R.id.navigationMenus ->{
                    val fragment = BarMenuFragment(getToken())
                    supportFragmentManager.beginTransaction().
                    replace(R.id.fragmentContainerViewBarProducts,fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss()
                    true
                }
                R.id.navigationSnacks ->{
                    val fragment = BarSnackFragment(getToken())
                    supportFragmentManager.beginTransaction().
                    replace(R.id.fragmentContainerViewBarProducts,fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss()
                    true
                }
                R.id.navigationColdDrinks -> {
                    val fragment = BarColdDrinkFragment(getToken())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerViewBarProducts, fragment)
                        .addToBackStack(null).commitAllowingStateLoss()
                    true
                }
                R.id.navigationHotDrinks ->{
                    val fragment = BarHotDrinkFragment(getToken())
                    supportFragmentManager.beginTransaction().
                    replace(R.id.fragmentContainerViewBarProducts,fragment).
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
                val intent = Intent(this@BarProductsActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_product_settings_staff -> {
                val intent = Intent(this@BarProductsActivity, BarProductsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_requests_staff -> {
                val intent = Intent(this@BarProductsActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_historic_staff -> {
                val intent = Intent(this@BarProductsActivity, BarProfileActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
        return super.onOptionsItemSelected(item)
    }
}
package com.ipca.smartbar.staff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.ipca.smartbar.R

class StaffMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_staff, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
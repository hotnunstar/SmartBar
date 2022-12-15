package com.ipca.smartbar.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.ipca.smartbar.R

class ClientMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_client, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
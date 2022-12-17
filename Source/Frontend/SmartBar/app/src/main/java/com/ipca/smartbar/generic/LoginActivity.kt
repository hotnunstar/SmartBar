package com.ipca.smartbar.generic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.ipca.smartbar.R
import com.ipca.smartbar.client.ClientProductsActivity
import com.ipca.smartbar.staff.StaffMainActivity

class LoginActivity : AppCompatActivity() {

    private val spinnerOptions = arrayOf("CLIENTE", "COLABORADOR")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var side = ""

        var spinner = findViewById<Spinner>(R.id.spinnerChooseSide)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerOptions)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                side = spinnerOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                side = spinnerOptions[0]
            }
        }

        // TEMPORÁRIO
        val onButtonLoginPressed: ((View)->Unit)= {
            Toast.makeText(applicationContext,side,Toast.LENGTH_LONG).show()
            if(side == "CLIENTE")
            {
                val intent = Intent(this@LoginActivity, ClientProductsActivity::class.java)
                startActivity(intent)
            }
            if(side == "COLABORADOR")
            {
                val intent = Intent(this@LoginActivity, StaffMainActivity::class.java)
                startActivity(intent)
            }
        }

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonLogin.setOnClickListener(onButtonLoginPressed)
    }
}
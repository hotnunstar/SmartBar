package com.ipca.smartbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

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

        val onButtonLoginPressed: ((View)->Unit)= {
            Toast.makeText(applicationContext,side,Toast.LENGTH_LONG).show()
        }

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonLogin.setOnClickListener(onButtonLoginPressed)
    }
}
package com.ipca.smartbar.generic

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.*
import com.ipca.smartbar.client.products.ClientProductsActivity
import com.ipca.smartbar.staff.StaffMainActivity
import java.util.*


class LoginActivity : AppCompatActivity() {

    private val spinnerOptions = arrayOf("CLIENTE", "COLABORADOR")
    private val loginModel = LoginModel("", "", "")

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            var token = getToken()
            if(token != null)
            {
                if(checkTokenExpiration(token))
                {
                    val intent = Intent(this@LoginActivity, ClientProductsActivity::class.java)
                    startActivity(intent)
                }
                else deleteToken()
            }

            val spinner = findViewById<Spinner>(R.id.spinnerChooseSide)
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerOptions)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    loginModel.userType = spinnerOptions[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    loginModel.userType = spinnerOptions[0]
                }
            }

            val onButtonLoginPressed: ((View)->Unit)= {
                val loginPassword = findViewById<EditText>(R.id.editTextLoginPassword).text.toString()
                val loginEmail = findViewById<EditText>(R.id.editTextLoginEmail).text.toString()
                loginModel.email = loginEmail
                loginModel.password = loginPassword

                if (loginModel.email.isNotEmpty() && loginModel.password.isNotEmpty()) {
                    if (loginModel.userType == "CLIENTE") {
                        LoginRequests.postLogin(lifecycleScope, loginModel){
                            token = it
                            if(token!!.isBlank()) { Toast.makeText(this,"Credênciais inválidas!", Toast.LENGTH_SHORT).show() }
                            if(token!!.isNotEmpty() && token!!.isNotBlank()) {
                                postToken(token!!)
                                val intent = Intent(this@LoginActivity, ClientProductsActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                    if (loginModel.userType == "COLABORADOR") {
                    LoginRequests.postLogin(lifecycleScope, loginModel){
                            token = it
                            if(token!!.isBlank()) { Toast.makeText(this,"Credênciais inválidas!", Toast.LENGTH_SHORT).show() }
                            if(token!!.isNotEmpty() && token!!.isNotBlank()) {
                                postToken(token!!)
                                val intent = Intent(this@LoginActivity, StaffMainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                        val intent = Intent(this@LoginActivity, StaffMainActivity::class.java)
                        startActivity(intent)
                    }
                }
                else {
                    Toast.makeText(this, "Deve inserir email e password!", Toast.LENGTH_LONG).show()
                }
            }
            val buttonLogin = findViewById<Button>(R.id.buttonLogin)
            buttonLogin.setOnClickListener(onButtonLoginPressed)
        }
}
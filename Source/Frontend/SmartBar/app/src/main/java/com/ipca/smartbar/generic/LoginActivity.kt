package com.ipca.smartbar.generic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.client.ClientProductsActivity
import com.ipca.smartbar.staff.StaffMainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val spinnerOptions = arrayOf("CLIENTE", "COLABORADOR")
    private val loginModel = LoginModel("", "", "")

    var token: String = ""

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            var spinner = findViewById<Spinner>(R.id.spinnerChooseSide)
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
                val loginPassword =
                    findViewById<EditText>(R.id.editTextLoginPassword).text.toString()
                val loginEmail = findViewById<EditText>(R.id.editTextLoginEmail).text.toString()
                loginModel.email = loginEmail
                loginModel.password = loginPassword

                if (loginModel.email.isNotEmpty() && loginModel.password.isNotEmpty()) {
                    if (loginModel.userType == "CLIENTE") {
                        LoginRequests.postLogin(loginModel)
                        /*val response = LoginRequests.buildService(LoginService::class.java)
                        response.postLogin(loginModel).enqueue(
                            object : Callback<LoginResponse> {
                                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                                    Log.d("RESPONSE", response.toString())
                                    Toast.makeText(this@LoginActivity, response.toString(), Toast.LENGTH_LONG).show()
                                }
                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                    Log.d("TESTE", t.toString())
                                    Toast.makeText(this@LoginActivity, t.toString(), Toast.LENGTH_LONG).show()
                                }
                            })*/
                    }
                        // val intent = Intent(this@LoginActivity, ClientProductsActivity::class.java)
                        // startActivity(intent)
                    if (loginModel.userType == "COLABORADOR") {
                        Log.d(TAG, "PASSEI")
                        // val intent = Intent(this@LoginActivity, StaffMainActivity::class.java)
                        // startActivity(intent)
                    }
                }
                else {
                    Toast.makeText(this, "Deve inserir email e password!", Toast.LENGTH_LONG).show()
                }
            }
            val buttonLogin = findViewById<Button>(R.id.buttonLogin)
            buttonLogin.setOnClickListener(onButtonLoginPressed)
        }

        companion object {
        const val TAG = "LoginActivity"
    }
}
package com.ipca.smartbar.generic

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object LoginRequests {

    private const val BASE_URL = "https://localhost:7097/api/Auth"
    private val client = OkHttpClient()

    fun loginRequest(scope: CoroutineScope,
                     loginModel: LoginModel,
                     callback: String)
    {
        scope.launch(Dispatchers.IO) {
            val request = loginModel.password?.let {
                loginModel.email?.let { it1 ->
                    loginModel.userType?.let { it2 ->
                        Request.Builder()
                            .url(BASE_URL)
                            .addHeader("Password", it)
                            .addHeader("Email", it1)
                            .addHeader("UserType", it2)
                            .build()
                    }
                }
            }
            if (request != null) {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val result = response.body!!.string()
                    Log.d(LoginActivity.TAG, result)

                    val jsonObject = JSONObject(result)
                    if (jsonObject.getString("status") == "ok"){
                        val token = jsonObject.get("jwtToken")
                        }
                        scope.launch (Dispatchers.Main){
                           // callback(token)
                }
            }
        }
    }
    }
}
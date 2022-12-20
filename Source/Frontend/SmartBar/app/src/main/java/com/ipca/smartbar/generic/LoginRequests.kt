package com.ipca.smartbar.generic

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginRequests {
    private val client = OkHttpClient.Builder().build()
    private const val baseurl = "https://192.168.1.74:7097"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> loginRequest(service: Class<T>): T{
        return retrofit.create(service)
    }

    /*fun loginRequest(loginModel: LoginModel) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = loginModel.toJSON()
            val call = getRetrofit().create(LoginService::class.java).postLogin(loginModel)
            Log.d("CALL", call.toString())
            //val token = call.body()

            //Log.d("TOKEN", token.toString())
        }
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://localhost:7097")
            .build()
    }*/
}
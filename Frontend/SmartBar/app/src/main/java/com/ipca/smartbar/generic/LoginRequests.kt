package com.ipca.smartbar.generic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import com.ipca.smartbar.Constants
import com.ipca.smartbar.ApiServices
import okhttp3.OkHttpClient
import java.io.IOException

object LoginRequests {

    private val client = OkHttpClient()

    fun postLogin(scope: CoroutineScope,
                  loginModel: LoginModel,
                  callback: (String)->Unit) {
        scope.launch (Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val service = retrofit.create(ApiServices::class.java)
            val loginModelJson = loginModel.toJSON()
            val loginModelJsonString = loginModelJson.toString()
            val requestBody = loginModelJsonString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.postLogin(requestBody)

            if (response.isSuccessful)
            {
                val jsonString = response.body()!!.string()
                scope.launch (Dispatchers.Main){ callback(jsonString) }
            }
            else scope.launch (Dispatchers.Main){ callback("") }
        }
    }
}
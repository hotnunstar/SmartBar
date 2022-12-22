package com.ipca.smartbar.generic

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import com.fasterxml.jackson.module.kotlin.*
import okhttp3.OkHttpClient

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

            val service = retrofit.create(LoginService::class.java)
            val loginModelJson = loginModel.toJSON()
            val loginModelJsonString = loginModelJson.toString()
            val requestBody = loginModelJsonString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.postLogin(requestBody)

            scope.launch (Dispatchers.Main){
                if (response.isSuccessful) {
                    val jsonString = response.body()?.string()
                    val mapper = jacksonObjectMapper()

                    if (jsonString != null) {
                        val responseObject = mapper.readValue<LoginResponse>(jsonString)
                        if (responseObject.token.isNotEmpty()) { callback(responseObject.token) }
                        else callback("")
                    }
                }
                else callback("")
            }
        }
    }
}
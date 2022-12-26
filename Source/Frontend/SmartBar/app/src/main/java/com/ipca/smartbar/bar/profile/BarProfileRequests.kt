package com.ipca.smartbar.bar.profile

import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit

object BarProfileRequests {
    private val client = OkHttpClient()

    fun getBarProfile(scope: CoroutineScope,
                      token: String?,
                      callback: (BarProfileModel)->Unit) {
        scope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val service = retrofit.create(ApiServices::class.java)
            val response = service.getBarProfile(token)

            if (response.isSuccessful) {
                val result = response.body()!!.string()
                val jsonObject = JSONObject(result)
                val bar = BarProfileModel.fromJSON(jsonObject)

                scope.launch(Dispatchers.Main) {
                    callback(bar)
                }
            } else {
                val bar = BarProfileModel("", "", "")
                scope.launch(Dispatchers.Main) {
                    callback(bar)
                }
            }
        }
    }
}
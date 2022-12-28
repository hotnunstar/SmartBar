package com.ipca.smartbar.client.profile

import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit

object ClientProfileRequests {
    private val client = OkHttpClient()

    fun getUserProfile(scope: CoroutineScope,
                       token: String?,
                       callback: (ClientProfileModel)->Unit){
        scope.launch(Dispatchers.IO){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val service = retrofit.create(ApiServices::class.java)
            val response = service.getUserProfile(token)

            if(response.isSuccessful)
            {
                val result = response.body()!!.string()
                val jsonObject = JSONObject(result)
                val user = ClientProfileModel.fromJSON(jsonObject)

                scope.launch(Dispatchers.Main) {
                    callback(user)
                }
            }
            else{
                val user = ClientProfileModel(0.0,"", "")
                scope.launch(Dispatchers.Main) {
                    callback(user)
                }
            }
        }
    }
}
package com.ipca.smartbar.client.historic

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONArray
import retrofit2.Retrofit

object CHBackendRequests {
    private val client = OkHttpClient()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getHistoric(scope: CoroutineScope,
                    token: String?,
                    callback: (ArrayList<ClientHistoricModel>)->Unit){
        scope.launch(Dispatchers.IO){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val historic = ArrayList<ClientHistoricModel>()
            val service = retrofit.create(ApiServices::class.java)
            val response = service.getCHByClientID(token)

            if (response.isSuccessful){
                val result = response.body()!!.string()
                val jsonArray = JSONArray(result)
                for (index in 0 until jsonArray.length()){
                    Log.e("batatas", jsonArray.toString())
                    val clientHistoricJSONObject = jsonArray.getJSONObject(index)
                    val clientHistoric = ClientHistoricModel.fromJSON(clientHistoricJSONObject)
                    historic.add(clientHistoric)

                    scope.launch(Dispatchers.Main){
                        callback(historic)
                    }
                }
            }
            else{
                scope.launch(Dispatchers.Main){
                    callback(historic)
                }
            }
        }
    }
}
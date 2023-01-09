package com.ipca.smartbar.bar.historic

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

object BHBackendRequests {
    private val client = OkHttpClient()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllHistoric(scope: CoroutineScope,
                    token: String?,
                    callback: (ArrayList<BarHistoricModel>)->Unit){
        scope.launch(Dispatchers.IO){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val historic = ArrayList<BarHistoricModel>()
            val service = retrofit.create(ApiServices::class.java)
            val response = service.getAllHistoric(token)

            if (response.isSuccessful){
                val result = response.body()!!.string()
                val jsonArray = JSONArray(result)
                for (index in 0 until jsonArray.length()){
                    Log.e("cebolas", jsonArray.toString())
                    val barHistoricJSONObject = jsonArray.getJSONObject(index)
                    val barHistoric = BarHistoricModel.fromJSON(barHistoricJSONObject)
                    historic.add(barHistoric)

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
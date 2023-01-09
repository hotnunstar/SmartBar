package com.ipca.smartbar.bar.requests

import android.util.Log
import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONArray
import retrofit2.Retrofit

object BarRequestsRequests {
    private val client = OkHttpClient()

    fun getRequests(scope: CoroutineScope,
                    token: String?,
                    state: Int,
                    callback: (ArrayList<BarRequestsModel>)->Unit){
        scope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val requests = ArrayList<BarRequestsModel>()
            val service = retrofit.create(ApiServices::class.java)
            val response = service.getRequests(state, token)

            if(response.isSuccessful){
                val result = response.body()!!.string()
                val jsonArrayRequests = JSONArray(result)
                for(index in 0 until jsonArrayRequests.length()){
                    val jsonArrayProducts = JSONArray(jsonArrayRequests.getJSONObject(2))
                    Log.e("PRODUCTS", jsonArrayProducts.toString())
                    /*for(product in 0 until jsonArra)

                    val requestJSONObject = jsonArrayRequests.getJSONObject(index)
                    for(product in 0 until requestJSONObject.length())
                    Log.e("REQUEST JSON OBJECT", requestJSONObject.toString())
                    //val request = BarRequestsModel.fromJSON(requestJSONObject)
                    //Log.e("REQUEST", request.idCliente)
                    //requests.add(request)*/
                }

                scope.launch(Dispatchers.Main) {
                    callback(requests)
                }
            }
            else{
                scope.launch(Dispatchers.Main) {
                    callback(requests)
                }
            }
        }
    }
}
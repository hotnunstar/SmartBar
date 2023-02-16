package com.ipca.smartbar.bar.requests

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.Constants
import com.ipca.smartbar.bar.products.BarProductsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit

object BarRequestsRequests {
    private val client = OkHttpClient()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getRequests(
        scope: CoroutineScope,
        token: String?,
        state: Int,
        callback: (ArrayList<BarRequestsModel>) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val requests = ArrayList<BarRequestsModel>()
            val service = retrofit.create(ApiServices::class.java)
            val response = service.getRequests(state, token)

            if (response.isSuccessful) {
                val result = response.body()!!.string()
                val jsonArrayRequests = JSONArray(result)
                for (index in 0 until jsonArrayRequests.length()) {
                    val products = ArrayList<BarProductLineModel>()
                    val jsonObject = jsonArrayRequests[index] as JSONObject
                    val idRequest = jsonObject.getString("idRequest")
                    val idCliente = jsonObject.getString("idCliente")
                    val jsonProductsLineArray = jsonObject.getJSONArray("productAndQuantity")
                    val dateRequest = jsonObject.getString("dateRequest")
                    val value = jsonObject.getDouble("value")
                    val state = jsonObject.getInt("state")
                    val firebaseToken = jsonObject.getString("firebaseToken")
                    val idBar = jsonObject.getString("idBar")
                    val horas = jsonObject.getString("horas")

                    for (i in 0 until jsonProductsLineArray.length()) {
                        val productsJSONObject = jsonProductsLineArray.getJSONObject(i)
                        val product = BarProductLineModel.fromJSON(productsJSONObject)
                        products.add(product)
                    }
                    val request: BarRequestsModel = (BarRequestsModel(
                        idRequest, idCliente, idBar,
                        products, dateRequest, value, state, horas, firebaseToken
                    ))
                    requests.add(request)
                }

                scope.launch(Dispatchers.Main) {
                    callback(requests)
                }
            } else {
                scope.launch(Dispatchers.Main) {
                    callback(requests)
                }
            }
        }
    }

    fun getProductByID(
        scope: CoroutineScope,
        token: String?,
        productsLines: List<BarProductLineModel>,
        callback: (ArrayList<BarProductsModel>) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val service = retrofit.create(ApiServices::class.java)
            val products: ArrayList<BarProductsModel> = ArrayList()

            for (index in productsLines.indices) {
                val response = service.getProductByID(productsLines[index].idProduct, token)
                if (response.isSuccessful) {
                    val result = response.body()!!.string()
                    val jsonObject = JSONObject(result)
                    val product = BarProductsModel.fromJSON(jsonObject)
                    products.add(product)
                }
            }
            scope.launch(Dispatchers.Main) { callback(products) }
        }
    }

    fun putRequest(scope: CoroutineScope,
                   token: String?,
                   idRequest: String,
                   confirmed: Boolean,
                   callback: (String) -> Unit){
        scope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val service = retrofit.create(ApiServices::class.java)
            val response = service.putRequests(idRequest, confirmed, token)

            if(response.isSuccessful) scope.launch(Dispatchers.Main) { callback(response.body()!!.string()) }
            else scope.launch(Dispatchers.Main) { callback(response.errorBody()!!.string()) }
        }
    }
}
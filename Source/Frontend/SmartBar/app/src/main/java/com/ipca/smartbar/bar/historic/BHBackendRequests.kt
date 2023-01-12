package com.ipca.smartbar.bar.historic

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.Constants
import com.ipca.smartbar.bar.products.BarProductsModel
import com.ipca.smartbar.bar.requests.BarProductLineModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit

object BHBackendRequests {
    private val client = OkHttpClient()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getBHByBarAndDate(scope: CoroutineScope,
                          token: String?,
                          dateReq: String,
                          callback: (ArrayList<BarHistoricModel>)->Unit){
        scope.launch(Dispatchers.IO){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val historics = ArrayList<BarHistoricModel>()
            val service = retrofit.create(ApiServices::class.java)
            val response = service.getBHByBarAndDate(dateReq, token)
            if (response.isSuccessful) {
                val result = response.body()!!.string()
                val jsonArrayHistoric = JSONArray(result)
                for (index in 0 until jsonArrayHistoric.length()) {
                    //Log.e("batatas", jsonArrayHistoric.toString())
                    val products = ArrayList<BarProductLineModel>()
                    val jsonObject = jsonArrayHistoric[index] as JSONObject

                    val idRequest = jsonObject.getString("idRequest")
                    val idClient = jsonObject.getString("idClient")
                    val jsonProductsLineArray = jsonObject.getJSONArray("productAndQuantity")
                    val dateRequest = jsonObject.getString("dateRequest")
                    val totalPrice = jsonObject.getDouble("totalPrice")
                    val state = jsonObject.getString("state")
                    val idBar = jsonObject.getString("idBar")
                    val horas = jsonObject.getString("horas")

                    for (i in 0 until jsonProductsLineArray.length()) {
                        val productsJSONObject = jsonProductsLineArray.getJSONObject(i)
                        val product = BarProductLineModel.fromJSON(productsJSONObject)
                        products.add(product)
                    }
                    val historic: BarHistoricModel = (BarHistoricModel(
                        idRequest, idClient,
                        products, dateRequest, totalPrice, state, idBar, horas
                    ))

                    historics.add(historic)
                }

                scope.launch(Dispatchers.Main) {
                    callback(historics)
                }
            }
            else {
                scope.launch(Dispatchers.Main) {
                    callback(historics)
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

}
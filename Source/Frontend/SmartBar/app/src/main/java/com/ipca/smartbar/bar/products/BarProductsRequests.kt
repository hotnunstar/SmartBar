package com.ipca.smartbar.bar.products

import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import retrofit2.Retrofit

object BarProductsRequests {
    private val client = OkHttpClient()

    fun getMenus(scope: CoroutineScope,
                token: String?,
                callback: (ArrayList<BarProductsModel>)->Unit){
        scope.launch(Dispatchers.IO){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val products = ArrayList<BarProductsModel>()
            val service = retrofit.create(ApiServices::class.java)
            val response = service.getMenus(token)

            if(response.isSuccessful){
                val result = response.body()!!.string()
                val jsonArray = JSONArray(result)
                for (index in 0 until jsonArray.length()){
                    val productJSONObject = jsonArray.getJSONObject(index)
                    val product = BarProductsModel.fromJSON(productJSONObject)
                    products.add(product)

                    scope.launch(Dispatchers.Main){
                        callback(products)
                    }
                }
            }
            else{
                scope.launch(Dispatchers.Main){
                    callback(products)
                }
            }
        }
    }

    fun getSnacks(scope: CoroutineScope,
                  token: String?,
                  callback: (ArrayList<BarProductsModel>)->Unit){
        scope.launch(Dispatchers.IO){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val products = ArrayList<BarProductsModel>()
            val service = retrofit.create(ApiServices::class.java)
            val response = service.getSnacks(token)

            if(response.isSuccessful){
                val result = response.body()!!.string()
                val jsonArray = JSONArray(result)
                for (index in 0 until jsonArray.length()){
                    val productJSONObject = jsonArray.getJSONObject(index)
                    val product = BarProductsModel.fromJSON(productJSONObject)
                    products.add(product)

                    scope.launch(Dispatchers.Main){
                        callback(products)
                    }
                }
            }
            else{
                scope.launch(Dispatchers.Main){
                    callback(products)
                }
            }
        }
    }

    fun getHotDrinks(scope: CoroutineScope,
                     token: String?,
                     callback: (ArrayList<BarProductsModel>)->Unit){
        scope.launch(Dispatchers.IO){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val products = ArrayList<BarProductsModel>()
            val service = retrofit.create(ApiServices::class.java)
            val response = service.getHotDrinks(token)

            if(response.isSuccessful){
                val result = response.body()!!.string()
                val jsonArray = JSONArray(result)
                for (index in 0 until jsonArray.length()){
                    val productJSONObject = jsonArray.getJSONObject(index)
                    val product = BarProductsModel.fromJSON(productJSONObject)
                    products.add(product)

                    scope.launch(Dispatchers.Main){
                        callback(products)
                    }
                }
            }
            else{
                scope.launch(Dispatchers.Main){
                    callback(products)
                }
            }
        }
    }

    fun getColdDrinks(scope: CoroutineScope,
                      token: String?,
                      callback: (ArrayList<BarProductsModel>)->Unit){
        scope.launch(Dispatchers.IO){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val products = ArrayList<BarProductsModel>()
            val service = retrofit.create(ApiServices::class.java)
            val response = service.getColdDrinks(token)

            if(response.isSuccessful){
                val result = response.body()!!.string()
                val jsonArray = JSONArray(result)
                for (index in 0 until jsonArray.length()){
                    val productJSONObject = jsonArray.getJSONObject(index)
                    val product = BarProductsModel.fromJSON(productJSONObject)
                    products.add(product)

                    scope.launch(Dispatchers.Main){
                        callback(products)
                    }
                }
            }
            else{
                scope.launch(Dispatchers.Main){
                    callback(products)
                }
            }
        }
    }

    fun postProduct(scope: CoroutineScope,
                  token: String?,
                  barProductsModel: BarProductsModel,
                  callback: (String)->Unit){
        scope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val service = retrofit.create(ApiServices::class.java)
            val barProductModelJson = barProductsModel.toJSON()
            val barProductModelJsonString = barProductModelJson.toString()
            val requestBody = barProductModelJsonString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.postProductBar(token, requestBody)

            if(response.isSuccessful) scope.launch(Dispatchers.Main) { callback("OK") }
            else {
                if(response.errorBody()!!.string() == "NOME REPETIDO") scope.launch(Dispatchers.Main) { callback("REPEATED") }
                else scope.launch(Dispatchers.Main) { callback("NotOK") }
            }
        }
    }

    fun putProduct(scope: CoroutineScope,
                 token: String?,
                 barProductsModel: BarProductsModel,
                 callback: (String)->Unit){
        scope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val service = retrofit.create(ApiServices::class.java)
            val barProductModelJson = barProductsModel.toJSON()
            val barProductModelJsonString = barProductModelJson.toString()
            val requestBody = barProductModelJsonString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.putProductBar(token, requestBody)

            if(response.isSuccessful) scope.launch(Dispatchers.Main) { callback("OK") }
            else scope.launch(Dispatchers.Main) { callback("NotOK") }
        }
    }
}
package com.ipca.smartbar.bar.products

import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
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

    fun postMenus(scope: CoroutineScope,
                  token: String?,
                  callback: (BarProductsModel)->Unit){

    }

    fun postSnacks(scope: CoroutineScope,
                   token: String?,
                   callback: (BarProductsModel)->Unit){

    }

    fun postHotDrinks(scope: CoroutineScope,
                      token: String?,
                      callback: (BarProductsModel)->Unit){

    }

    fun postColdDrinks(scope: CoroutineScope,
                       token: String?,
                       callback: (BarProductsModel)->Unit){

    }

    fun putMenus(scope: CoroutineScope,
                 token: String?,
                 callback: (BarProductsModel)->Unit){

    }

    fun putSnacks(scope: CoroutineScope,
                  token: String?,
                  callback: (BarProductsModel)->Unit){

    }

    fun putHotDrinks(scope: CoroutineScope,
                     token: String?,
                     callback: (BarProductsModel)->Unit){

    }

    fun putColdDrinks(scope: CoroutineScope,
                      token: String?,
                      callback: (BarProductsModel)->Unit){

    }
}
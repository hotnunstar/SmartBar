package com.ipca.smartbar.client.products.api

import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.client.cart.Pedido
import com.ipca.smartbar.client.products.Product
import okhttp3.ResponseBody
import retrofit2.Response

class BackendDataSource {
    var client = Backend.getInstance()
    var apiServices = client.create(ApiServices::class.java)

    suspend fun getProductsHotFood(token:String?): Response<List<Product>>
    {
        return apiServices.getProductsHotFood(token)
    }

    suspend fun getProductsPackaged(token:String?):Response<List<Product>>
    {
        return apiServices.getProductsPackaged(token)
    }
    suspend fun getProductsHotDrink(token:String?) : Response<List<Product>>
    {
        return apiServices.getProductsHotDrink(token)
    }
    suspend fun getProductsColdDrink(token:String?) : Response<List<Product>>
    {
        return apiServices.getProductsColdDrink(token)
    }
    suspend fun postPedido(pedido:Pedido,token:String?): Response<ResponseBody>
    {
        return apiServices.postPedido(pedido,token)
    }
}
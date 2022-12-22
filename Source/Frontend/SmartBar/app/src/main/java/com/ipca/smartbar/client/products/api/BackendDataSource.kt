package com.ipca.smartbar.client.products.api

import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.client.products.Product
import retrofit2.Response

class BackendDataSource {
    var client = Backend.getInstance()
    var apiServices = client.create(ApiServices::class.java)

    suspend fun getProductsHotFood(): Response<List<Product>>
    {
        return apiServices.getProductsHotFood()
    }

    suspend fun getProductsPackaged():Response<List<Product>>
    {
        return apiServices.getProductsPackaged()
    }
    suspend fun getProductsHotDrink() : Response<List<Product>>
    {
        return apiServices.getProductsHotDrink()
    }
    suspend fun getProductsColdDrink() : Response<List<Product>>
    {
        return apiServices.getProductsColdDrink()
    }
}
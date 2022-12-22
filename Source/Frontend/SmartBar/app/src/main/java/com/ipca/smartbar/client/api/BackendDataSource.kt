package com.ipca.smartbar.client.api

import com.ipca.smartbar.client.models.Product
import retrofit2.Response

class BackendDataSource {
    var client = Backend.getInstance()
    var service = client.create(Service::class.java)

    suspend fun getProductsHotFood(): Response<List<Product>>
    {
        return service.getProductsHotFood()
    }

    suspend fun getProductsPackaged():Response<List<Product>>
    {
        return service.getProductsPackaged()
    }
}
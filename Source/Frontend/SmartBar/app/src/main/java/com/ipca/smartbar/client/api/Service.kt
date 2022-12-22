package com.ipca.smartbar.client.api

import com.ipca.smartbar.client.models.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("/api/Product/hotfood")
    suspend fun getProductsHotFood(): Response<List<Product>>

    @GET("/api/Product/packaged")
    suspend fun getProductsPackaged(): Response<List<Product>>

}
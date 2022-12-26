package com.ipca.smartbar

import com.ipca.smartbar.client.products.Product
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServices {
    @GET("/api/Product/hotdrink")
    suspend fun getProductsHotDrink(): Response<List<Product>>

    @GET("/api/Product/packaged")
    suspend fun getProductsPackaged(): Response<List<Product>>

    @GET("/api/Product/coldrink")
    suspend fun getProductsColdDrink(): Response<List<Product>>

    @GET("/api/Product/hotfood")
    suspend fun getProductsHotFood(): Response<List<Product>>

    @Headers("Content-Type: application/json")
    @POST("/api/Auth")
    suspend fun postLogin(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/api/User/GetUserById")
    suspend fun getProfile(@Header("Authorization") token: String?): Response<ResponseBody>
}
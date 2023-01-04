package com.ipca.smartbar

import com.ipca.smartbar.client.cart.Pedido
import com.ipca.smartbar.client.products.Product
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiServices {

    //region Products Client
    @GET("/api/Product/Menus")
    suspend fun getProductsHotFood(): Response<List<Product>>

    @GET("/api/Product/Snacks")
    suspend fun getProductsPackaged(): Response<List<Product>>

    @GET("/api/Product/HotDrink")
    suspend fun getProductsHotDrink(): Response<List<Product>>

    @GET("/api/Product/ColDrink")
    suspend fun getProductsColdDrink(@Header("Authorization") token: String?): Response<List<Product>>
    //endregion

    //region Requests
    @Headers("Content-Type: application/json")
    @POST("/api/pedido")
    suspend fun postPedido(@Body pedido:Pedido): Response<ResponseBody>
    //endregion

    //region Login
    @Headers("Content-Type: application/json")
    @POST("/api/Auth")
    suspend fun postLogin(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/api/User/GetUserById")
    suspend fun getUserProfile(@Header("Authorization") token: String?): Response<ResponseBody>

    @GET("/api/Bar/GetBarById")
    suspend fun getBarProfile(@Header("Authorization") token: String?): Response<ResponseBody>
    //endregion

    //region Products Bar
    @GET("/api/Product/Menus")
    suspend fun getMenus(@Header("Authorization") token: String?): Response<ResponseBody>

    @GET("/api/Product/Snacks")
    suspend fun getSnacks(@Header("Authorization") token: String?): Response<ResponseBody>

    @GET("/api/Product/HotDrink")
    suspend fun getHotDrinks(@Header("Authorization") token: String?): Response<ResponseBody>

    @GET("/api/Product/ColdDrink")
    suspend fun getColdDrinks(@Header("Authorization") token: String?): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/api/Product")
    suspend fun postProductBar(@Header("Authorization") token: String?, @Body requestBody: RequestBody): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @PUT("/api/Product")
    suspend fun putProductBar(@Header("Authorization") token: String?, @Body requestBody: RequestBody): Response<ResponseBody>
    //endregion
}
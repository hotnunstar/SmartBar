package com.ipca.smartbar


import com.ipca.smartbar.client.cart.BarProfileModel
import com.ipca.smartbar.client.cart.Pedido
import com.ipca.smartbar.client.products.Product
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    //region Products Client
    @GET("/api/Product/Menus")
    suspend fun getProductsHotFood(@Header("Authorization") token: String?): Response<List<Product>>

    @GET("/api/Bar")
    suspend fun getAllBares(@Header("Authorization") token: String?): Response<List<BarProfileModel>>

    @GET("/api/Product/Snacks")
    suspend fun getProductsPackaged(@Header("Authorization") token: String?): Response<List<Product>>

    @GET("/api/Product/HotDrink")
    suspend fun getProductsHotDrink(@Header("Authorization") token: String?): Response<List<Product>>

    @GET("/api/Product/ColdDrink")
    suspend fun getProductsColdDrink(@Header("Authorization") token: String?): Response<List<Product>>
    //endregion

    //region Requests
    @Headers("Content-Type: application/json")
    @POST("/api/Request")
    suspend fun postPedido(@Body pedido:Pedido, @Header("Authorization") token: String?): Response<ResponseBody>

    @GET("/api/Request/{state}")
    suspend fun getRequests(@Path("state") state: Int, @Header("Authorization") token: String?): Response<ResponseBody>

    @PUT("/api/Request/{idRequest}")
    suspend fun putRequests(@Path("idRequest") idRequest: String, @Query("confirmed") confirmed: Boolean, @Header("Authorization") token: String?): Response<ResponseBody>
    //endregion

    //region Historic Client
    @GET("/api/Historic/GetHistoricByClient")
    suspend fun getCHByClientID(@Header("Authorization") token: String?): Response<ResponseBody>
    //endregion

    //region Historic Bar
    @GET("/api/Historic/GetHistoric")
    suspend fun getAllHistoric(@Header("Authorization") token: String?): Response<ResponseBody>
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

    @GET("/api/Product/{productID}")
    suspend fun getProductByID(@Path("productID") productID: String, @Header("Authorization") token: String?): Response<ResponseBody>
    //endregion

    //region Notifications
    @GET("/api/PushNotification")
    suspend fun getPushNotifications(@Header("Authorization") token: String?): Response<ResponseBody>

    @DELETE("/api/PushNotification")
    suspend fun deletePushNotification(@Query("notificationId") notificationId: String, @Header("Authorization") token: String?): Response<ResponseBody>
    //endregion
}
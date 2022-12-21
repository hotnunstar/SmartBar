package com.ipca.smartbar.generic

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST("/api/Auth")
    suspend fun postLogin(@Body requestBody: RequestBody): Response<ResponseBody>
}
package com.ipca.smartbar.generic

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/Auth")
    fun postLogin(@Body loginModel: LoginModel): Call<LoginResponse>
}
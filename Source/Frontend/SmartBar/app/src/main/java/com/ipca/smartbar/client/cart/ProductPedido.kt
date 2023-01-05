package com.ipca.smartbar.client.cart

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductPedido(
    @Json(name = "id")
    var id: String,
    @Json(name = "quantidade")
    var quantity: Int)

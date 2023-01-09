package com.ipca.smartbar.client.cart

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductPedido(
    @Json(name = "idProduct")
    var id: String,
    @Json(name = "quantity")
    var quantity: Int)

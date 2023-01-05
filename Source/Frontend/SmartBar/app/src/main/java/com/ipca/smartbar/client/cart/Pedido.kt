package com.ipca.smartbar.client.cart


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pedido(
    @Json(name = "id")
    var id: String?=null,
    @Json(name = "listProducts")
    var listProducts: List<ProductPedido>?,
    @Json(name = "preco")
    var preco: Double=0.0,
    @Json(name = "bar")
    var bar:String
)

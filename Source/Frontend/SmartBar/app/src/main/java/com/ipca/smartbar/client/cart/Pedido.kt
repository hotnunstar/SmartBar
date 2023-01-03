package com.ipca.smartbar.client.cart

import androidx.room.PrimaryKey
import com.ipca.smartbar.client.products.Product
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pedido(
    @Json(name = "id")
    var id: String?=null,
    @Json(name = "listProducts")
    var listProducts: ArrayList<Product>?,
    @Json(name = "preco")
    var preco: Double=0.0,
    @Json(name = "bar")
    var bar:String
)

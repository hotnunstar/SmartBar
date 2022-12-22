package com.ipca.smartbar.client.products
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product (
    @Json(name = "name")
    var nome: String?=null,
    @Json(name = "image")
    var image: String?=null,
    @Json(name = "price")
    var preco: Double?=0.0,
    @Json(name = "stock")
    var stock:Int?=0
)



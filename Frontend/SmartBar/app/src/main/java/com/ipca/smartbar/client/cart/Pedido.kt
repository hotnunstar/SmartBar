package com.ipca.smartbar.client.cart


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate
import java.util.Date

@JsonClass(generateAdapter = true)
data class Pedido(
    @Json(name = "IdRequest")
    var id: String?=null,
    @Json(name = "productAndQuantity")
    var listProducts: List<ProductPedido>?,
    @Json(name = "value")
    var preco: Double=0.0,
    @Json(name = "idBar")
    var bar:String,
    @Json(name = "idCliente")
    var idCliente:String,
    @Json(name = "state")
    var state:Int,
    @Json(name = "firebaseToken")
    var firebaseToken:String,
    @Json(name = "horas")
    var horas:String,
    @Json(name = "dateRequest")
var dateRequest:String
)

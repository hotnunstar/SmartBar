package com.ipca.smartbar.client.products


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Product(
    @PrimaryKey
    @Json(name = "id")
    var id: String,
    @Json(name = "name")
    var nome: String?,
    @Json(name = "image")
    var image: String?=null,
    @Json(name = "price")
    var preco: Double=0.0,
    @Json(name = "stock")
    var stock:Int?=0,
    @Json(name = "quantity")
    var quantity:Int=1
)


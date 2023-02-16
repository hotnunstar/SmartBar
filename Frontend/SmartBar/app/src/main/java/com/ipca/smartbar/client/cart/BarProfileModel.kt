package com.ipca.smartbar.client.cart

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BarProfileModel(
    @Json(name = "id")
    var id: String,
    @Json(name = "description")
    var description: String,
    @Json(name = "email")
    var email: String
    )

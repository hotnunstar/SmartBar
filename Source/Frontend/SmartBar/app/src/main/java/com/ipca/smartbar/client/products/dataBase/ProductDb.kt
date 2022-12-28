package com.ipca.smartbar.client.products.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ProductDb (
    @PrimaryKey
    var nome: String,
    var image: String?=null,
    var preco: Double?=0.0,
    var stock:Int?=0)

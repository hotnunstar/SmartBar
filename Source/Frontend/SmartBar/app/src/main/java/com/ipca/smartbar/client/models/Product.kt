package com.ipca.smartbar.client.models

class Product {
    var nome: String?=null
    var image: String?=null
    var preco: Double?=0.0

    constructor(nome: String?, image: String?, preco: Double?) {
        this.nome = nome
        this.image = image
        this.preco = preco
    }
}
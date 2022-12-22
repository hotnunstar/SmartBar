package com.ipca.smartbar.client.cart

import java.time.LocalDateTime

class Pedido {
    var idProduto: String? = null
    var idPedido: String? = null
    var data: LocalDateTime? = null
    var valor: Double? = null
    var bar: String? = null

    constructor(idProduto: String, idPedido: String?, data: LocalDateTime?, valor: Double?, bar: String?) {
        this.idProduto = idProduto
        this.idPedido = idPedido
        this.data = data
        this.valor = valor
        this.bar = bar
    }
}
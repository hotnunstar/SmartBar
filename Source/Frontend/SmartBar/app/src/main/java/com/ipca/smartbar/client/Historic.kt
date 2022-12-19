package com.ipca.smartbar.client

import java.time.Instant
import java.time.LocalDateTime

class Historic {
    //var idClient: String? = null
    var idPedido: String? = null
    var data: LocalDateTime? = null
    var valor: Double? = null
    var Estado: Int? = null

    constructor(idPedido: String?, data: LocalDateTime?, valor: Double?, Estado: Int?) {
        this.idPedido = idPedido
        this.data = data
        this.valor = valor
        this.Estado = Estado
    }
}
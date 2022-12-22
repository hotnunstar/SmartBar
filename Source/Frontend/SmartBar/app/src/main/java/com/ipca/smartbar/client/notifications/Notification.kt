package com.ipca.smartbar.client.notifications

import java.time.LocalDateTime

class Notification {
    var idPedido: String? = null
    var dataAtual: LocalDateTime? = null
    var notificacao: String? = null

    constructor(idPedido: String?, dataAtual: LocalDateTime, notificacao: String?) {
        this.idPedido = idPedido
        this.dataAtual = dataAtual
        this.notificacao = notificacao
    }
}
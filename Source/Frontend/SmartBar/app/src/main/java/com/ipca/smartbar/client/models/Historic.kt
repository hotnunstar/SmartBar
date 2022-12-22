<<<<<<<< HEAD:Source/Frontend/SmartBar/app/src/main/java/com/ipca/smartbar/client/historic/Historic.kt
package com.ipca.smartbar.client.historic
========
package com.ipca.smartbar.client.models
>>>>>>>> d5147a3ec1b0c1def51ff3793cc4974dda848258:Source/Frontend/SmartBar/app/src/main/java/com/ipca/smartbar/client/models/Historic.kt

import java.time.LocalDateTime
import java.util.*

class Historic {
    //var idClient: String? = null
    var idPedido: String? = null
    var data: LocalDateTime? = null
    var valor: Double? = null
    var Estado: Int? = null

    constructor(idPedido: String?, data: LocalDateTime, valor: Double?, Estado: Int?) {
        this.idPedido = idPedido
        this.data = data
        this.valor = valor
        this.Estado = Estado
    }
}
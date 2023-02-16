package com.ipca.smartbar.bar.requests

class BarRequestsModel {
    var idRequest         : String = ""
    var idCliente         : String = ""
    var idBar             : String = ""
    var productAndQuantity: List<BarProductLineModel>? = null
    var dateRequest       : String = ""
    var value             : Double = 0.0
    var state             : Int = 0
    var horas             : String = ""
    var firebaseToken     : String = ""

    constructor(
        idRequest: String,
        idCliente: String,
        idBar: String,
        productAndQuantity: List<BarProductLineModel>,
        dateRequest: String,
        value: Double,
        state: Int,
        horas: String,
        firebaseToken: String
    ) {
        this.idRequest = idRequest
        this.idCliente = idCliente
        this.idBar = idBar
        this.productAndQuantity = productAndQuantity
        this.dateRequest = dateRequest
        this.value = value
        this.state = state
        this.horas = horas
        this.firebaseToken = firebaseToken
    }
}
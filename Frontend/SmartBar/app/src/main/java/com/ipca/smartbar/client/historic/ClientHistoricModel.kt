package com.ipca.smartbar.client.historic

import android.os.Build
import androidx.annotation.RequiresApi
import com.ipca.smartbar.bar.requests.BarProductLineModel
import org.json.JSONArray
import org.json.JSONObject



class ClientHistoricModel{
    var idRequest : String? = ""
    var idClient : String? = ""
    var productAndQuantity : List<BarProductLineModel>? = null
    var dateRequest : String? = ""
    var totalPrice : Double? = 0.0
    var state: String? = ""
    var idBar : String? = ""
    var horas: String? = ""


    constructor(
        idRequest: String,
        idClient: String,
        productAndQuantity: List<BarProductLineModel>,
        dateRequest: String,
        totalPrice: Double,
        state: String,
        idBar: String,
        horas: String
    ) {
        this.idRequest = idRequest
        this.idClient = idClient
        this.productAndQuantity = productAndQuantity
        this.dateRequest = dateRequest
        this.totalPrice = totalPrice
        this.state = state
        this.idBar = idBar
        this.horas = horas
    }
}
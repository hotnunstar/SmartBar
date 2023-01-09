package com.ipca.smartbar.client.historic

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONArray
import org.json.JSONObject



class ClientHistoricModel{
    var idRequest : String? = ""
    var idClient : String? = ""
    var productAndQuantity : JSONArray? = null
    var dateRequest : String? = ""
    var totalPrice : Double? = 0.0
    var state: String? = ""
    var idBar : String? = ""
    var horas: String? = ""


    @RequiresApi(Build.VERSION_CODES.O)
    constructor(
        idRequest: String,
        idClient: String,
        productAndQuantity: JSONArray,
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun toJSON() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("idRequest", idRequest)
        jsonObject.put("idClient", idClient)
        jsonObject.put("productAndQuantity", productAndQuantity)
        jsonObject.put("dateRequest", dateRequest)
        jsonObject.put("totalPrice", totalPrice)
        jsonObject.put("state", state)
        jsonObject.put("idBar", idBar)
        jsonObject.put("horas", horas)
        return jsonObject
    }

    companion object
    {
        @RequiresApi(Build.VERSION_CODES.O)
        fun fromJSON(jsonObject: JSONObject) : ClientHistoricModel {
            return ClientHistoricModel(
                jsonObject.getString("idRequest"),
                jsonObject.getString("idClient"),
                jsonObject.getJSONArray("productAndQuantity"),
                jsonObject.getString("dateRequest"),
                jsonObject.getDouble("totalPrice"),
                jsonObject.getString("state"),
                jsonObject.getString("idBar"),
                jsonObject.getString("horas")
            )
        }
    }
}
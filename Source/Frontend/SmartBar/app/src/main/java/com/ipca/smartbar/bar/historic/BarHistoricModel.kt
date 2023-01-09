package com.ipca.smartbar.bar.historic

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONArray
import org.json.JSONObject

class BarHistoricModel {
    var idRequest : String? = ""
    var idClient : String? = ""
    var productAndQuantity : JSONArray? = null
    var dateExpected : String? = ""
    var dateRequest : String? = ""
    var totalPrice : Double? = 0.0
    var state: String? = ""


    @RequiresApi(Build.VERSION_CODES.O)
    constructor(
        idRequest: String,
        idClient: String,
        productAndQuantity: JSONArray,
        dateExpected: String,
        dateRequest: String,
        totalPrice: Double,
        state: String
    ) {
        this.idRequest = idRequest
        this.idClient = idClient
        this.productAndQuantity = productAndQuantity
        this.dateExpected = dateExpected
        this.dateRequest = dateRequest
        this.totalPrice = totalPrice
        this.state = state
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toJSON() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("idRequest", idRequest)
        jsonObject.put("idClient", idClient)
        jsonObject.put("productAndQuantity", productAndQuantity)
        jsonObject.put("dateExpected", dateExpected)
        jsonObject.put("dateRequest", dateRequest)
        jsonObject.put("totalPrice", totalPrice)
        jsonObject.put("state", state)
        return jsonObject
    }

    companion object
    {
        @RequiresApi(Build.VERSION_CODES.O)
        fun fromJSON(jsonObject: JSONObject) : BarHistoricModel {
            return BarHistoricModel(
                jsonObject.getString("idRequest"),
                jsonObject.getString("idClient"),
                jsonObject.getJSONArray("productAndQuantity"),
                jsonObject.getString("dateExpected"),
                jsonObject.getString("dateRequest"),
                jsonObject.getDouble("totalPrice"),
                jsonObject.getString("state")
            )
        }
    }
}
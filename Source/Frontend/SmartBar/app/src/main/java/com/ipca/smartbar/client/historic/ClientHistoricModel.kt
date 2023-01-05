package com.ipca.smartbar.client.historic

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONArray
import org.json.JSONObject



class ClientHistoricModel{
    var idRequest : String? = ""
    var idClient : String? = ""
    var idProduct : JSONArray? = null
    var dateExpected : String? = ""
    var dateRequest : String? = ""
    var totalPrice : Double? = 0.0
    var state: String? = ""


    @RequiresApi(Build.VERSION_CODES.O)
    constructor(
        idRequest: String,
        idClient: String,
        idProduct: JSONArray,
        dateExpected: String,
        dateRequest: String,
        totalPrice: Double,
        state: String
    ) {
        this.idRequest = idRequest
        this.idClient = idClient
        this.idProduct = idProduct
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
        jsonObject.put("idProduct", idProduct)
        jsonObject.put("dateExpected", dateExpected)
        jsonObject.put("dateRequest", dateRequest)
        jsonObject.put("totalPrice", totalPrice)
        jsonObject.put("state", state)
        return jsonObject
    }

    companion object
    {
        @RequiresApi(Build.VERSION_CODES.O)
        fun fromJSON(jsonObject: JSONObject) : ClientHistoricModel {
            return ClientHistoricModel(
                jsonObject.getString("idRequest"),
                jsonObject.getString("idClient"),
                jsonObject.getJSONArray("idProduct"),
                jsonObject.getString("dateExpected"),
                jsonObject.getString("dateRequest"),
                jsonObject.getDouble("totalPrice"),
                jsonObject.getString("state")
            )
        }
    }
}
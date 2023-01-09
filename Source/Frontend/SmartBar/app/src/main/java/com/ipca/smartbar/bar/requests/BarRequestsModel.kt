package com.ipca.smartbar.bar.requests

import com.ipca.smartbar.bar.products.BarProductsModel
import org.json.JSONObject
import java.util.Date

class BarRequestsModel {
    var idRequest         : String = ""
    var idCliente          : String = ""
    var productAndQuantity: List<BarProductLineModel>? = null
    var datePickUp        : Date? = null
    var dateRequest       : Date? = null
    var value             : Double = 0.0
    var state             : Int = 0
    var firebaseToken     : String = ""

    constructor(
        idRequest           : String,
        idCliente           : String,
        productAndQuantity  : List<BarProductLineModel>?,
        datePickUp          : Date?,
        dateRequest         : Date?,
        value               : Double,
        state               : Int,
        firebaseToken       : String
    ) {
        this.idRequest          = idRequest
        this.idCliente          = idCliente
        this.productAndQuantity = productAndQuantity
        this.datePickUp         = datePickUp
        this.dateRequest        = dateRequest
        this.value              = value
        this.state              = state
        this.firebaseToken      = firebaseToken
    }

    fun toJSON(): JSONObject{
        val jsonObject = JSONObject()
        jsonObject.put("idRequest"          , idRequest          )
        jsonObject.put("idCliente"          , idCliente         )
        jsonObject.put("productAndQuantity" , productAndQuantity )
        jsonObject.put("datePickUp"         , datePickUp         )
        jsonObject.put("dateRequest"        , dateRequest        )
        jsonObject.put("value"              , value              )
        jsonObject.put("state"              , state              )
        jsonObject.put("firebaseToken"      , firebaseToken      )
        return jsonObject
    }

    companion object{
        fun fromJSON(jsonObject: JSONObject): BarRequestsModel{
            return BarRequestsModel(
                jsonObject.getString("idRequest"),
                jsonObject.getString("idCliente"),
                jsonObject.get("productAndQuantity") as List<BarProductLineModel>?,
                jsonObject.get("datePickUp") as Date?,
                jsonObject.get("dateRequest") as Date?,
                jsonObject.getDouble("value"),
                jsonObject.getInt("state"),
                jsonObject.getString("firebaseToken"),
            )
        }
    }
}
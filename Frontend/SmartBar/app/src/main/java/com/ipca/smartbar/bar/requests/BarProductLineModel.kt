package com.ipca.smartbar.bar.requests

import org.json.JSONObject

class BarProductLineModel {
    var idProduct   : String = ""
    var quantity    : Int    = 0

    constructor(idProduct: String, quantity: Int) {
        this.idProduct = idProduct
        this.quantity = quantity
    }

    fun toJson() : JSONObject{
        val jsonObject = JSONObject()
        jsonObject.put("idProduct", idProduct)
        jsonObject.put("quantity" , quantity )
        return jsonObject
    }

    companion object{
        fun fromJSON(jsonObject: JSONObject) : BarProductLineModel {
            return BarProductLineModel(
                jsonObject.getString("idProduct"),
                jsonObject.getInt("quantity")
            )
        }
    }
}
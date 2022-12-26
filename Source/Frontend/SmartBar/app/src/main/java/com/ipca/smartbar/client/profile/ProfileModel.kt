package com.ipca.smartbar.client.profile

import org.json.JSONObject

class ProfileModel {

    var balance : Double = 0.0
    var name    : String = ""
    var email   : String = ""

    constructor(balance: Double, name: String, email: String) {
        this.balance = balance
        this.name = name
        this.email = email
    }


    fun toJSON() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("balance", balance )
        jsonObject.put("name"   , name    )
        jsonObject.put("email"  , email   )
        return jsonObject
    }

    companion object
    {
        fun fromJSON(jsonObject: JSONObject) : ProfileModel {
            return ProfileModel(
                jsonObject.getDouble("balance" ),
                jsonObject.getString("name"    ),
                jsonObject.getString("email"   )
            )
        }
    }
}
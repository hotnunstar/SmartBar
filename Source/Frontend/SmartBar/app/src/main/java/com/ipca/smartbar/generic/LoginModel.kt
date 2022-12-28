package com.ipca.smartbar.generic

import org.json.JSONObject

class LoginModel(var userType: String, var email: String, var password: String) {

    fun toJSON() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("userType", userType )
        jsonObject.put("email"   , email    )
        jsonObject.put("password", password )
        return jsonObject
    }

    fun fromJSON(jsonObject: JSONObject) : LoginModel {
        return LoginModel(
            jsonObject.getString("usertype" ),
            jsonObject.getString("email"    ),
            jsonObject.getString("password" )
        )
    }
}
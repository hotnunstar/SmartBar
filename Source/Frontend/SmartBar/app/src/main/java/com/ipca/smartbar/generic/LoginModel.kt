package com.ipca.smartbar.generic

import android.provider.ContactsContract.CommonDataKinds.Email
import org.json.JSONObject

class LoginModel {
    var userType : String? = null
    var email: String? = null
    var password: String? = null

    constructor(userType: String?, email: String?, password: String?) {
        this.userType = userType
        this.email = email
        this.password = password
    }

    fun toJSON() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("UserType", userType )
        jsonObject.put("Email"   , email    )
        jsonObject.put("Password", password )
        return jsonObject
    }

    fun fromJSON(jsonObject: JSONObject) : LoginModel {
        return LoginModel(
            jsonObject.getString("Usertype" ),
            jsonObject.getString("Email"    ),
            jsonObject.getString("Password" )
        )
    }
}
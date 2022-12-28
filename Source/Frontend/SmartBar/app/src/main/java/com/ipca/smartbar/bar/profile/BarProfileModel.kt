package com.ipca.smartbar.bar.profile

import org.json.JSONObject

class BarProfileModel {
    var id          : String = ""
    var description : String = ""
    var email       : String = ""

    constructor(id: String, description: String, email: String) {
        this.id = id
        this.description = description
        this.email = email
    }

    fun toJSON() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("id"         , id         )
        jsonObject.put("description", description)
        jsonObject.put("email"      , email      )
        return jsonObject
    }

    companion object
    {
        fun fromJSON(jsonObject: JSONObject) : BarProfileModel {
            return BarProfileModel(
                jsonObject.getString("id"         ),
                jsonObject.getString("description"),
                jsonObject.getString("email"      )
            )
        }
    }
}
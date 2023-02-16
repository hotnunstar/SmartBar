package com.ipca.smartbar.client.notifications

import org.json.JSONObject

class ClientNotificationsModel {
    var id      : String = ""
    var token   : String = ""
    var userId  : String = ""
    var title   : String = ""
    var message : String = ""
    var date    : String = ""

    constructor(
        id: String,
        token: String,
        userId: String,
        title: String,
        message: String,
        date: String
    ) {
        this.id = id
        this.token = token
        this.userId = userId
        this.title = title
        this.message = message
        this.date = date
    }

    fun toJSON() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("id"     , id     )
        jsonObject.put("token"  , token  )
        jsonObject.put("userId" , userId )
        jsonObject.put("title"  , title  )
        jsonObject.put("message", message)
        jsonObject.put("date"   , date   )
        return jsonObject
    }

    companion object{
        fun fromJSON(jsonObject: JSONObject) : ClientNotificationsModel {
            return ClientNotificationsModel(
                jsonObject.getString("id"     ),
                jsonObject.getString("token"  ),
                jsonObject.getString("userId" ),
                jsonObject.getString("title"  ),
                jsonObject.getString("message"),
                jsonObject.getString("date"   )
            )
        }
    }
}
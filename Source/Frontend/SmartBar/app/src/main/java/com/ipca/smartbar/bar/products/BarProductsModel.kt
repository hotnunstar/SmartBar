package com.ipca.smartbar.bar.products

import org.json.JSONObject

class BarProductsModel {
    var id     : String? = null
    var name   : String? = null
    //var picture: ByteArray = "asd"
    var price  : Double? = 0.0
    var stock  : Int? = null
    var type   : Int? = null

    constructor(
        id: String,
        name: String,
        //picture: ByteArray,
        price: Double,
        stock: Int,
        type: Int
    ) {
        this.id = id
        this.name = name
        //this.picture = picture
        this.price = price
        this.stock = stock
        this.type = type
    }

    fun toJson() : JSONObject{
        val jsonObject = JSONObject()
        jsonObject.put("id"     , id     )
        jsonObject.put("name"   , name   )
        //jsonObject.put("picture", picture)
        jsonObject.put("price"  , price  )
        jsonObject.put("stock"  , stock  )
        jsonObject.put("type"   , type   )
        return jsonObject
    }

    companion object{
        fun fromJSON(jsonObject: JSONObject) : BarProductsModel {
            return BarProductsModel(
                jsonObject.getString("id"),
                jsonObject.getString("name"),
                //jsonObject.get("picture") as ByteArray,
                jsonObject.getDouble("price"),
                jsonObject.getInt("stock"),
                jsonObject.getInt("type")
            )
        }
    }
}
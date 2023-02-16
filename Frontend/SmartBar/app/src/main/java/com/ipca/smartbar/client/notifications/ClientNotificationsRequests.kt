package com.ipca.smartbar.client.notifications

import com.ipca.smartbar.ApiServices
import com.ipca.smartbar.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONArray
import retrofit2.Retrofit

object ClientNotificationsRequests {

    private val client = OkHttpClient()

    fun getNotifications(scope: CoroutineScope,
                         token: String?,
                         callback: (ArrayList<ClientNotificationsModel>)->Unit){
        scope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val notifications = ArrayList<ClientNotificationsModel>()
            val service = retrofit.create(ApiServices::class.java)
            val response = service.getPushNotifications(token)

            if(response.isSuccessful){
                val result = response.body()!!.string()
                val jsonArray = JSONArray(result)
                for(index in 0 until jsonArray.length()){
                    val notificationJSONObject = jsonArray.getJSONObject(index)
                    val notification = ClientNotificationsModel.fromJSON(notificationJSONObject)
                    notifications.add(notification)
                }
                scope.launch(Dispatchers.Main) {
                    callback(notifications)
                }
            } else {
                scope.launch(Dispatchers.Main){
                    callback(notifications)
                }
            }
        }
    }

    fun deleteNotification(scope: CoroutineScope,
                           notificationId: String,
                           token: String?,
                           callback: (String)->Unit){
        scope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseurl)
                .client(client)
                .build()

            val service = retrofit.create(ApiServices::class.java)
            val response = service.deletePushNotification(notificationId, token)

            if (response.isSuccessful) scope.launch(Dispatchers.Main) { callback("OK") }
            else scope.launch(Dispatchers.Main) { callback("NotOK") }
        }
    }
}
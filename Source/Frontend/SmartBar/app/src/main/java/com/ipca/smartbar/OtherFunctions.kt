package com.ipca.smartbar

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.auth0.jwt.JWT
import java.time.LocalDate
import java.time.ZoneId

fun Context.postToken(token: String){
    val sharedPreference =  getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putString("TOKEN", token)
    editor.apply()
}

fun Context.getToken(): String? {
    val sharedPreference = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
    return sharedPreference.getString("TOKEN", null)
}

@RequiresApi(Build.VERSION_CODES.O)
fun checkTokenExpiration(token: String): Boolean{
    val jwt = JWT.decode(token)
    val tokenLocalDate: LocalDate = jwt.expiresAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val currentDate = LocalDate.now()
    return tokenLocalDate > currentDate
}

fun Context.deleteToken() {
    val sharedPreference =  getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.remove("TOKEN")
    editor.apply()
}
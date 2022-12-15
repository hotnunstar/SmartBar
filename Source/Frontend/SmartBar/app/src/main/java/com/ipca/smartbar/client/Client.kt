package com.ipca.smartbar.client

import android.provider.ContactsContract.CommonDataKinds.Email

class Client {
    var balance: Double? = null
    var name: String? = null
    var email: Email? = null

    constructor(balance: Double?, name: String?, email: Email?) {
        this.balance = balance
        this.name = name
        this.email = email
    }
}
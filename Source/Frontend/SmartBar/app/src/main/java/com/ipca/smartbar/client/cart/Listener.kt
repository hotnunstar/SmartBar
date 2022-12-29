package com.ipca.smartbar.client.cart

import com.ipca.smartbar.client.products.Product

interface Listener {
    fun add(product: Product)
}
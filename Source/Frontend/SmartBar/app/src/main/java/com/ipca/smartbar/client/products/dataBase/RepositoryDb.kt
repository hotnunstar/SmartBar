package com.ipca.smartbar.client.products.dataBase

import androidx.lifecycle.LiveData

import com.ipca.smartbar.client.products.Product

class RepositoryDb(private val productDao : ProductDAO) {

    val readAllProducts : LiveData<List<Product>> = productDao.getAll()


    suspend fun addProduct(product: Product){
        productDao.insertAll(product)
    }
}
package com.ipca.smartbar.client.products.api

import com.ipca.smartbar.client.products.Product

object Repository {
    var backendDataSource = BackendDataSource()

    suspend fun getProductsHotFood() :  Pair<ArrayList<Product>,Boolean>
    {
        val response = backendDataSource.getProductsHotFood()
        val result = response.body()
        return if(response.isSuccessful && result != null) {
            Pair(ArrayList(result),true)
        } else {
            Pair(ArrayList(),false)
        }
    }
    suspend fun getProductsPackaged(): Pair<ArrayList<Product>,Boolean>
    {
        val response = backendDataSource.getProductsPackaged()
        val result = response.body()
        return if(response.isSuccessful && result != null) {
            Pair(ArrayList(result),true)
        } else {
            Pair(ArrayList(),false)
        }
    }
    suspend fun getProductsHotDrink(): Pair<ArrayList<Product>,Boolean>
    {
        val response = backendDataSource.getProductsHotDrink()
        val result = response.body()
        return if(response.isSuccessful && result != null) {
            Pair(ArrayList(result),true)
        } else {
            Pair(ArrayList(),false)
        }
    }
    suspend fun getProductsColdDrink(): Pair<ArrayList<Product>,Boolean>
    {
        val response = backendDataSource.getProductsColdDrink()
        val result = response.body()
        return if(response.isSuccessful && result != null) {
            Pair(ArrayList(result),true)
        } else {
            Pair(ArrayList(),false)
        }
    }
}
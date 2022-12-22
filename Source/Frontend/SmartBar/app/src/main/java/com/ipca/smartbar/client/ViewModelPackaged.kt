package com.ipca.smartbar.client

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipca.smartbar.client.products.api.Repository
import com.ipca.smartbar.client.products.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelPackaged : ViewModel() {

    val products = MutableLiveData<Pair<ArrayList<Product>,Boolean>>()

    fun getProducts()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            products.postValue(Repository.getProductsPackaged())
        }
    }
}
package com.ipca.smartbar.client

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipca.smartbar.client.api.Repository
import com.ipca.smartbar.client.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelHotDrink : ViewModel() {
    val products = MutableLiveData<Pair<ArrayList<Product>,Boolean>>()

    fun getProducts()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            products.postValue(Repository.getProductsHotFood())
        }
    }

}
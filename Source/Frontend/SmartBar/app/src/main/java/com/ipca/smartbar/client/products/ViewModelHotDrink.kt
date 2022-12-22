package com.ipca.smartbar.client.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipca.smartbar.client.products.api.Repository
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
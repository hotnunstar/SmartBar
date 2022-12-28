package com.ipca.smartbar.client.products



import android.app.Application
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipca.smartbar.client.products.api.Repository
import com.ipca.smartbar.client.products.dataBase.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelProducts() : ViewModel(){
    val products = MutableLiveData<Pair<ArrayList<Product>,Boolean>>()


    fun getProductsColdDrink()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            products.postValue(Repository.getProductsColdDrink())
        }
    }
    fun getProductsPackaged()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            products.postValue(Repository.getProductsPackaged())
        }
    }
    fun getProductsHotFood()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            products.postValue(Repository.getProductsHotFood())
        }
    }
    fun getProductsHotDrink()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            products.postValue(Repository.getProductsHotDrink())
        }
    }

}
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
    val products = MutableLiveData<Pair<ArrayList<Product>,String>>()


    fun getProductsColdDrink(token:String?)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            products.postValue(Repository.getProductsColdDrink(token))
        }
    }
    fun getProductsPackaged(token:String?)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            products.postValue(Repository.getProductsPackaged(token))
        }
    }
    fun getProductsHotFood(token:String?)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            products.postValue(Repository.getProductsHotFood(token))
        }
    }
    fun getProductsHotDrink(token:String?)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            products.postValue(Repository.getProductsHotDrink(token))
        }
    }

}
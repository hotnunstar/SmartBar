package com.ipca.smartbar.client.products




import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipca.smartbar.client.cart.BarProfileModel
import com.ipca.smartbar.client.products.api.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelProducts() : ViewModel(){
    val products = MutableLiveData<Pair<ArrayList<Product>,String>>()
    val bares = MutableLiveData<Pair<ArrayList<BarProfileModel>,String>>()


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
    fun getAllBares(token:String?)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            bares.postValue(Repository.getAllBares(token))
        }
    }
}



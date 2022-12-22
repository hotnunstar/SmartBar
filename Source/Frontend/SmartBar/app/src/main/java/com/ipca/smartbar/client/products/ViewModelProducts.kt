package com.ipca.smartbar.client.products

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipca.smartbar.client.products.api.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelProducts : ViewModel(){
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
    fun addProductInList(product:Product)
    {
        product.preco = product.preco!!-1
        val bundle = Bundle()
        val intent = Intent()
        bundle.putSerializable(EXTRA_PRODUCT,product)
        intent.putExtras(bundle)



    }
    companion object{
       const val EXTRA_PRODUCT = "extra_product"
    }
}
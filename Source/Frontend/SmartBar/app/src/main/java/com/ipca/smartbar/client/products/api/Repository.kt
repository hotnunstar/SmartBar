package com.ipca.smartbar.client.products.api




import com.ipca.smartbar.client.cart.BarProfileModel
import com.ipca.smartbar.client.cart.Pedido
import com.ipca.smartbar.client.products.Product


object Repository {
    var backendDataSource = BackendDataSource()

    suspend fun getProductsHotFood(token:String?) :  Pair<ArrayList<Product>,String>
    {
        val response = backendDataSource.getProductsHotFood(token)
        val result = response.body()
        return if(response.isSuccessful && result != null) {
            Pair(ArrayList(result),"")
        } else {
            Pair(ArrayList(),response.message())
        }
    }
    suspend fun getProductsPackaged(token:String?): Pair<ArrayList<Product>,String>
    {
        val response = backendDataSource.getProductsPackaged(token)
        val result = response.body()
        return if(response.isSuccessful && result != null) {
            Pair(ArrayList(result),"")
        } else {
            Pair(ArrayList(),response.message())
        }
    }
    suspend fun getProductsHotDrink(token:String?): Pair<ArrayList<Product>,String>
    {
        val response = backendDataSource.getProductsHotDrink(token)
        val result = response.body()
        return if(response.isSuccessful && result != null) {
            Pair(ArrayList(result),"")
        } else {
            Pair(ArrayList(),response.message())
        }
    }
    suspend fun getProductsColdDrink(token:String?): Pair<ArrayList<Product>,String>
    {

        val response = backendDataSource.getProductsColdDrink(token)
        val result = response.body()
        return if(response.isSuccessful && result != null) {
            Pair(ArrayList(result),"")
        } else {
            Pair(ArrayList(),response.message())
        }
    }
    suspend fun confirmarPedido(pedido: Pedido,token:String?): Pair<String,Boolean>
    {
        val response = backendDataSource.postPedido(pedido,token)
        if(response.isSuccessful)
        {
            return Pair(response.message(),true)
        }
        else
        {
            return Pair(response.errorBody()!!.string(),false)
        }

    }
    suspend fun getAllBares(token:String?) : Pair<ArrayList<BarProfileModel>,String>{
        val response = backendDataSource.getAllBares(token)
        val result=response.body()
        return if(response.isSuccessful && result != null) {
            Pair(ArrayList(result),"")
        } else {
            Pair(ArrayList(),response.message())
        }
    }
}
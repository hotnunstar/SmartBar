package com.ipca.smartbar.client.products.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ipca.smartbar.client.cart.ProductPedido
import com.ipca.smartbar.client.products.Product


@Dao
interface ProductDAO {
    @Query("SELECT * FROM product ORDER BY product.nome")
    fun getAll(): LiveData<List<Product>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg product: Product)
    @Delete
    fun delete(product: Product)
}
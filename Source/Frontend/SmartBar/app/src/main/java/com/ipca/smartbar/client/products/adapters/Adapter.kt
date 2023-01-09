package com.ipca.smartbar.client.products.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.RequiresApi
import com.ipca.smartbar.client.products.ColdDrinkFragment
import com.ipca.smartbar.client.products.Product
import com.ipca.smartbar.client.products.dataBase.ProductDb
import com.ipca.smartbar.databinding.RowProductsBinding
import java.util.*
import kotlin.collections.ArrayList


class Adapter(val context: Context, val products: ArrayList<Product>) : BaseAdapter(){

private lateinit var binding: RowProductsBinding
var clickListener : ((p:Product)->Unit)?=null

    override fun getCount(): Int {
        return products.size
    }
    override fun getItem(p0: Int): Any {
        return products[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        binding = RowProductsBinding.inflate(LayoutInflater.from(context),p2,false)
        binding.tvNomeProduct.text = products[p0].nome.toString()
        binding.tvPreco.text = products[p0].preco.toString()
        binding.btAddProduct.setOnClickListener(){
           clickListener?.invoke(products[p0])
        }
        if(products[p0].image != null)
        {
            fun resizeBitmap(source: Bitmap, maxLength: Int): Bitmap {
                try {
                    if (source.height >= source.width) {
                        if (source.height <= maxLength) return source

                        val aspectRatio = source.width.toDouble() / source.height.toDouble()
                        val targetWidth = (maxLength * aspectRatio).toInt()

                        return Bitmap.createScaledBitmap(source, targetWidth, maxLength, false)
                    } else {
                        if (source.width <= maxLength) return source

                        val aspectRatio = source.height.toDouble() / source.width.toDouble()
                        val targetHeight = (maxLength * aspectRatio).toInt()

                        return Bitmap.createScaledBitmap(source, maxLength, targetHeight, false)
                    }
                } catch (e: Exception) { return source }
            }
            val pictureByteArray = Base64.getDecoder().decode(products[p0].image)
            var bitmap = BitmapFactory.decodeByteArray(pictureByteArray, 0, pictureByteArray.size)
            bitmap = resizeBitmap(bitmap!!,300)
            binding.ivProductImage.setImageBitmap(bitmap)

        }
        return binding.root
    }



}
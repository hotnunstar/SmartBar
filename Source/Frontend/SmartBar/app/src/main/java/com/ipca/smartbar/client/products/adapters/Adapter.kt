package com.ipca.smartbar.client.products.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ipca.smartbar.client.products.Product
import com.ipca.smartbar.databinding.RowProductsBinding

class Adapter(val context: Context, val products: ArrayList<Product>) : BaseAdapter() {

private lateinit var binding: RowProductsBinding
    override fun getCount(): Int {
        return products.size
    }

    override fun getItem(p0: Int): Any {
        return products[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        binding = RowProductsBinding.inflate(LayoutInflater.from(context),p2,false)
        binding.tvNomeProduct.text = products[p0].nome.toString()
        binding.tvPreco.text = products[p0].preco.toString()
        return binding.root
    }
}
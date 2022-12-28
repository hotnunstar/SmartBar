package com.ipca.smartbar.client.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.ipca.smartbar.client.products.Product
import com.ipca.smartbar.databinding.RowCartBinding


class Adapter(val context: Context, val products: ArrayList<Product>) : BaseAdapter() {
    private lateinit var binding: RowCartBinding
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
        binding = RowCartBinding.inflate(LayoutInflater.from(context),p2,false)
        binding.textViewProductName.text=products[p0].nome
        binding.textViewQuantidade.text = products[p0].quantity.toString()
        binding.buttonAcrescentar.setOnClickListener {
            binding.textViewQuantidade.text = 20.toString()

        }
        binding.buttonRetirar.setOnClickListener {

        }
        return binding.root
    }
}
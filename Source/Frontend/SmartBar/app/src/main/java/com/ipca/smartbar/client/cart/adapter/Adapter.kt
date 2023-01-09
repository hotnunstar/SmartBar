package com.ipca.smartbar.client.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ipca.smartbar.client.cart.ProductsCardFragment
import com.ipca.smartbar.client.products.Product
import com.ipca.smartbar.databinding.RowCartBinding


class Adapter(val context: Context, val products: ArrayList<Product>,val listener: ProductsCardFragment) : BaseAdapter() {
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
        binding.textViewPrecoProduto.text = products[p0].preco.toString()
        binding.textViewQuantidade.text = products[p0].quantity.toString()
        binding.buttonAcrescentar.setOnClickListener {
            products[p0].quantity +=1
            listener.apdateProduct(products[p0],products)
            notifyDataSetChanged()
        }
        binding.buttonRetirar.setOnClickListener {
            if(products[p0].quantity ==1)
            products[p0].quantity =1
            else
            {
                products[p0].quantity -=1
                listener.valorTotal(products)
                listener.apdateProduct(products[p0],products)
                notifyDataSetChanged()
            }

        }
        binding.imageButtonDelete.setOnClickListener {
            listener.deleteProduct(products[p0])
            notifyDataSetChanged()
        }
        return binding.root
    }
}
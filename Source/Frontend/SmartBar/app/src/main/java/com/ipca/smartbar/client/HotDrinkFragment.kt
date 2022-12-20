package com.ipca.smartbar.client

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ipca.smartbar.client.adapters.Adapter
import com.ipca.smartbar.client.models.Product
import com.ipca.smartbar.databinding.FragmentHotDrinkBinding


class HotDrinkFragment : Fragment() {
    private lateinit var binding : FragmentHotDrinkBinding
    private var products = ArrayList<Product>()
    private lateinit var adapter: Adapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotDrinkBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        products.add(Product("lanche","image",12.0))
        products.add(Product("lanche","image",12.0))
        products.add(Product("lanche","image",12.0))
        products.add(Product("lanche","image",12.0))
        loadList(products)
    }
    private fun loadList(products:ArrayList<Product>)
    {
        val list = binding.lvProducts
        val context : Context = this.context as Context
        adapter = Adapter(context,products)
        list.adapter = adapter
    }
}
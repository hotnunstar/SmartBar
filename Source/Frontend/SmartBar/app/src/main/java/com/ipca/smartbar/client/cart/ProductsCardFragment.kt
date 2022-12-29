package com.ipca.smartbar.client.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.client.cart.adapter.Adapter
import com.ipca.smartbar.client.products.Product
import com.ipca.smartbar.client.products.dataBase.AppDatabase
import com.ipca.smartbar.databinding.FragmentProductsCardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductsCardFragment : Fragment(){

    private lateinit var binding: FragmentProductsCardBinding
    private lateinit var adapter: Adapter
    var products = ArrayList<Product>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retornaProdutosDb()

    }

    fun retornaProdutosDb() {
        AppDatabase.getDatabase(requireContext())?.productDao()?.getAll()
            ?.observe(viewLifecycleOwner, Observer(::bindValues))
    }

    private fun loadList(product: ArrayList<Product>) {
        val list = binding.lvProducts
        val context: Context = this.context as Context
        adapter = Adapter(context, product,this)
        list.adapter = adapter

    }

    private fun bindValues(list: List<Product>) {
        val lista: ArrayList<Product> = list as ArrayList<Product>
        loadList(lista)
        adapter.notifyDataSetChanged()

    }
     fun deleteProduct(product:Product)
    {
        lifecycleScope.launch(Dispatchers.IO)
        {
            AppDatabase.getDatabase(requireContext())?.productDao()?.delete(product)
        }

    }
}
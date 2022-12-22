package com.ipca.smartbar.client

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ipca.smartbar.client.adapters.Adapter
import com.ipca.smartbar.client.api.Service
import com.ipca.smartbar.client.common.Constants
import com.ipca.smartbar.client.models.Product
import com.ipca.smartbar.databinding.FragmentHotDrinkBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HotDrinkFragment : Fragment() {
    private lateinit var binding : FragmentHotDrinkBinding
    private lateinit var adapter: Adapter
    private val viewModel : ViewModelHotDrink by viewModels()

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
        /*products.add(Product("lanche","image",12.0))
        products.add(Product("lanche","image",12.0))
        products.add(Product("lanche","image",12.0))
        products.add(Product("lanche","image",12.0))*/

        viewModel.getProducts()
        setupObservers()



    }
    private fun loadList(products:ArrayList<Product>)
    {
        val list = binding.lvProducts
        val context : Context = this.context as Context
        adapter = Adapter(context,products)
        list.adapter = adapter
    }
    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner,Observer(::bindValues))
    }

    private fun  bindValues(pair: Pair<ArrayList<Product>,Boolean>) {
        if(pair.second)
        {
            loadList(pair.first)
            adapter.notifyDataSetChanged()
        } else
        {
            //fazer um toast
        }



    }


}
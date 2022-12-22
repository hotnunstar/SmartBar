package com.ipca.smartbar.client.products

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ipca.smartbar.client.products.adapters.Adapter
import com.ipca.smartbar.databinding.FragmentHotDrinkBinding


class HotDrinkFragment : Fragment() {
    private lateinit var binding : FragmentHotDrinkBinding
    private lateinit var adapter: Adapter
    private val viewModel : ViewModelProducts by viewModels()

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

        viewModel.getProductsHotDrink()
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
package com.ipca.smartbar.client.products

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.client.products.adapters.Adapter
import com.ipca.smartbar.client.products.dataBase.AppDatabase
import com.ipca.smartbar.databinding.FragmentHotDrinkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HotDrinkFragment(private val token:String?) : Fragment() {
    private lateinit var binding : FragmentHotDrinkBinding
    private lateinit var adapter: Adapter
    private val viewModel : ViewModelProducts by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotDrinkBinding.inflate(inflater,container,false)
        binding.progressBarHotDrink.visibility=View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductsHotDrink(token)
        setupObservers()



    }
    private fun loadList(products:ArrayList<Product>)
    {
        val list = binding.lvProducts
        val context : Context = this.context as Context
        adapter = Adapter(context,products,)
        adapter.clickListener = {product->
            lifecycleScope.launch(Dispatchers.IO) {
                AppDatabase.getDatabase(requireContext())?.productDao()?.insertAll(product)
            }

        }
        list.adapter = adapter
    }
    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner,Observer(::bindValues))
    }

    private fun  bindValues(pair: Pair<ArrayList<Product>,String>) {
        if(pair.second =="")
        {
            binding.progressBarHotDrink.visibility=View.GONE
            loadList(pair.first)
            adapter.notifyDataSetChanged()
        } else
        {
            Toast.makeText(
                context,
                "Nao existe produtos",
                Toast.LENGTH_SHORT
            ).show()
        }



    }


}
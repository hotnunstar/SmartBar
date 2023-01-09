package com.ipca.smartbar.client.products

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.client.products.adapters.Adapter
import com.ipca.smartbar.client.products.dataBase.AppDatabase
import com.ipca.smartbar.databinding.FragmentPackagedBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PackagedFragment(private val token:String?) : Fragment() {
    private lateinit var binding: FragmentPackagedBinding
    private lateinit var adapter: Adapter
    private val viewModel: ViewModelProducts by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPackagedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductsPackaged(token)
        setupObservers()
    }

    private fun loadList(products: ArrayList<Product>) {
        val list = binding.lvProducts
        val context: Context = this.context as Context
        adapter = Adapter(context,products)
        adapter.clickListener = {product->
            lifecycleScope.launch(Dispatchers.IO) {
                AppDatabase.getDatabase(requireContext())?.productDao()?.insertAll(product)
            }

        }
        list.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner, Observer(::bindValues))
    }

    private fun bindValues(pair: Pair<ArrayList<Product>,String>) {
        if (pair.second =="") {
            loadList(pair.first)
            adapter.notifyDataSetChanged()
        } else {
            //fazer um toast
        }
    }


}


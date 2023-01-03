package com.ipca.smartbar.bar.products.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.products.BarProductsModel
import com.ipca.smartbar.bar.products.BarProductsRequests
import com.ipca.smartbar.databinding.FragmentBarProductsBinding

class BarColdDrinkFragment(token: String?) : Fragment() {

    private var _binding: FragmentBarProductsBinding? = null
    private val binding get() = _binding!!
    private val adapter = ProductsAdapter()
    private var products = ArrayList<BarProductsModel>()
    private val token = token

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarProductsBinding.inflate(inflater, container, false)
        binding.buttonAddProduct.text = "ADICIONAR BEBIDAS FRIAS"
        binding.viewProgressBarBarProducts.visibility = View.VISIBLE
        binding.progressBarBarProducts.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BarProductsRequests.getColdDrinks(lifecycleScope, token){
            products = it
            adapter.notifyDataSetChanged()
            binding.viewProgressBarBarProducts.visibility = View.GONE
            binding.progressBarBarProducts.visibility = View.GONE
        }
        binding.listViewBarProducts.adapter = adapter

        val buttonAddProduct = binding.buttonAddProduct
        buttonAddProduct.setOnClickListener() {
            val fragment = AddProductFragment(4, token)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
            transaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class ProductsAdapter: BaseAdapter() {
        override fun getCount(): Int {
            return products.size
        }

        override fun getItem(position: Int): Any {
            return products[position]
        }

        override fun getItemId(position: Int): Long {
            return 0L
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_products_bar, parent, false)
            val textViewProductName = rowView.findViewById<TextView>(R.id.textViewProductName)
            val textViewProductPrice = rowView.findViewById<TextView>(R.id.textViewProductPrice)
            val imageButtonEditProduct = rowView.findViewById<ImageButton>(R.id.imageButtonEditProduct)

            val product = products[position]
            val price = product.price.toString()+"€"
            textViewProductName.text = product.name
            textViewProductPrice.text = price

            imageButtonEditProduct.setOnClickListener(){
                val fragment = EditProductFragment()
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
                transaction.commit()
            }
            return rowView
        }
    }
}
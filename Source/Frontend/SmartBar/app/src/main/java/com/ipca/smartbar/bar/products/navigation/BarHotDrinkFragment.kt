package com.ipca.smartbar.bar.products.navigation

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.products.BarProductsModel
import com.ipca.smartbar.bar.products.BarProductsRequests
import com.ipca.smartbar.databinding.FragmentBarProductsBinding
import java.util.*
import kotlin.collections.ArrayList

class BarHotDrinkFragment(private val token: String?) : Fragment() {
    private var _binding: FragmentBarProductsBinding? = null
    private val binding get() = _binding!!
    private val adapter = ProductsAdapter()
    private var products = ArrayList<BarProductsModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarProductsBinding.inflate(inflater, container, false)
        binding.buttonAddProduct.text = "ADICIONAR BEBIDAS QUENTES"
        binding.viewProgressBarBarProducts.visibility = View.VISIBLE
        binding.progressBarBarProducts.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BarProductsRequests.getHotDrinks(lifecycleScope, token){
            products = it
            adapter.notifyDataSetChanged()
            binding.viewProgressBarBarProducts.visibility = View.GONE
            binding.progressBarBarProducts.visibility = View.GONE
        }
        binding.listViewBarProducts.adapter = adapter

        val buttonAddProduct = binding.buttonAddProduct
        buttonAddProduct.setOnClickListener() {
            val fragment = AddProductFragment(3, token)
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

        @RequiresApi(Build.VERSION_CODES.O)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_products_bar, parent, false)
            val textViewProductName = rowView.findViewById<TextView>(R.id.textViewProductName)
            val textViewProductPrice = rowView.findViewById<TextView>(R.id.textViewProductPrice)
            val textViewProductStock = rowView.findViewById<TextView>(R.id.textViewProductStock)
            val imageButtonEditProduct = rowView.findViewById<ImageButton>(R.id.imageButtonEditProduct)
            val imageViewProduct = rowView.findViewById<ImageView>(R.id.imageViewProductBar)

            val product = products[position]
            val price = product.price.toString()+"€"
            textViewProductName.text = product.name
            textViewProductPrice.text = price
            textViewProductStock.text = product.stock.toString()
            val pictureByteArray = Base64.getDecoder().decode(product.picture)
            val bitmap = BitmapFactory.decodeByteArray(pictureByteArray, 0, pictureByteArray.size)
            imageViewProduct.setImageBitmap(bitmap)

            imageButtonEditProduct.setOnClickListener(){
                val fragment = EditProductFragment(product, token)
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
                transaction.commit()
            }
            return rowView
        }
    }
}
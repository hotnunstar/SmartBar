package com.ipca.smartbar.bar.products.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.products.BarProductsModel
import com.ipca.smartbar.bar.products.BarProductsRequests
import com.ipca.smartbar.databinding.FragmentAddProductBinding

class AddProductFragment(
    private val productType: Int,
    val token: String?) : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val product = (BarProductsModel("","",0.0,0,productType))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        changeTitle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextProductName = binding.editTextProductName
        val editTextProductPrice = binding.editTextNumberDecimalPrice
        val editTextProductStock = binding.editTextNumberStock
        val buttonInsertProduct = binding.buttonConfirmProductAdd
        val buttonCancel = binding.buttonCancellProductAdd

        buttonCancel.setOnClickListener(){
           goToFragment()
        }

        buttonInsertProduct.setOnClickListener(){
            if(editTextProductName.text.isNotEmpty() && editTextProductPrice.text.isNotEmpty() && editTextProductStock.text.isNotEmpty()) {
                product.name = editTextProductName.text.toString()
                product.price = editTextProductPrice.text.toString().toDouble()
                product.stock = editTextProductStock.text.toString().toInt()
                product.type = productType
                postProduct(product)
            }
            else Toast.makeText(activity, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToFragment(){
        if(productType == 1){
            val fragment = BarMenuFragment(token)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
            transaction.commit()
        }
        if(productType == 2){
            val fragment = BarSnackFragment(token)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
            transaction.commit()
        }
        if(productType == 3){
            val fragment = BarHotDrinkFragment(token)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
            transaction.commit()
        }
        if(productType == 4){
            val fragment = BarColdDrinkFragment(token)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
            transaction.commit()
        }
    }

    private fun postProduct(product: BarProductsModel){
        BarProductsRequests.postProduct(lifecycleScope, token, product){
            val result = it
            if (result == "OK"){
                Toast.makeText(activity,"PRODUTO INSERIDO!", Toast.LENGTH_SHORT).show()
                goToFragment()
            }
            else{
                Toast.makeText(activity, "ERRO NA INSERÇÃO DO PRODUTO", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeTitle()
    {
        val title = binding.textViewTitleAddProduct
        if(product.type == 1)title.text = "INSERIR MENU"
        if(product.type == 2)title.text = "INSERIR SNACK"
        if(product.type == 3)title.text = "INSERIR BEBIDA QUENTE"
        if(product.type == 4)title.text = "INSERIR BEBIDA FRIA"
    }
}
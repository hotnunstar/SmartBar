package com.ipca.smartbar.bar.products.navigation

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.products.BarProductsModel
import com.ipca.smartbar.bar.products.BarProductsRequests
import com.ipca.smartbar.databinding.FragmentEditProductBinding
import java.util.*

class EditProductFragment(
    private val product: BarProductsModel,
    val token: String?) : Fragment() {

    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        changeTitle()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextProductName = binding.editTextEditProductName
        val editTextProductPrice = binding.editTextNumberDecimalEditProductPrice
        val editTextProductStock = binding.editTextNumberEditProductStock
        val checkBoxStock = binding.checkBoxEditProductStock
        val buttonEditProduct = binding.buttonConfirmProductEdit
        val buttonCancel = binding.buttonCancelProductEdit
        val imageViewProduct = binding.imageViewEditProduct

        val pictureByteArray = Base64.getDecoder().decode(product.picture)
        val bitmap = BitmapFactory.decodeByteArray(pictureByteArray, 0, pictureByteArray.size)
        imageViewProduct.setImageBitmap(bitmap)

        editTextProductName.setTextKeepState(product.name)
        editTextProductPrice.setTextKeepState(product.price.toString())
        editTextProductStock.setTextKeepState(product.stock.toString())

        buttonCancel.setOnClickListener(){
            goToFragment()
        }

        buttonEditProduct.setOnClickListener(){
            if(editTextProductName.text.isNotEmpty() && editTextProductPrice.text.isNotEmpty() && editTextProductStock.text.isNotEmpty())
            {
                product.name = editTextProductName.text.toString()
                product.price = editTextProductPrice.text.toString().toDouble()
                if(checkBoxStock.isChecked) product.stock = 0
                else product.stock = editTextProductStock.text.toString().toInt()

                putProduct(product)
            }
            else Toast.makeText(activity, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToFragment(){
        if(product.type == 1){
            val fragment = BarMenuFragment(token)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
            transaction.commit()
        }
        if(product.type == 2){
            val fragment = BarSnackFragment(token)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
            transaction.commit()
        }
        if(product.type == 3){
            val fragment = BarHotDrinkFragment(token)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
            transaction.commit()
        }
        if(product.type == 4){
            val fragment = BarColdDrinkFragment(token)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewBarProducts, fragment)
            transaction.commit()
        }
    }

    private fun putProduct(product: BarProductsModel){
        BarProductsRequests.putProduct(lifecycleScope, token, product){
            val result = it
            if (result == "OK"){
                Toast.makeText(activity, "PRODUTO EDITADO!", Toast.LENGTH_SHORT).show()
                goToFragment()
            }
            else{
                Toast.makeText(activity, "ERRO NA EDIÇÃO DO PRODUTO", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeTitle()
    {
        val title = binding.textViewTitleEditProduct
        if(product.type == 1) title.text = "EDITAR MENU"
        if(product.type == 2) title.text = "EDITAR SNACK"
        if(product.type == 3) title.text = "EDITAR BEBIDA QUENTE"
        if(product.type == 4) title.text = "EDITAR BEBIDA FRIA"
    }
}
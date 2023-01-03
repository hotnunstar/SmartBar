package com.ipca.smartbar.bar.products.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.products.BarProductsActivity
import com.ipca.smartbar.bar.products.BarProductsModel
import com.ipca.smartbar.client.products.ClientProductsActivity
import com.ipca.smartbar.databinding.FragmentAddProductBinding

class AddProductFragment(productType: Int,
                        token: String?) : Fragment() {

    private val productType = productType
    private val token = token
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val product = (BarProductsModel("","",0.0,0,productType))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonInsertProduct = binding.buttonConfirmProductAdd
        val buttonCancel = binding.buttonCancellProductAdd

        buttonCancel.setOnClickListener(){
           goToFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goToFragment(){
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
}
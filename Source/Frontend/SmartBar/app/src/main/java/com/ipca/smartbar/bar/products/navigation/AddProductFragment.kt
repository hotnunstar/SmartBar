package com.ipca.smartbar.bar.products.navigation

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.products.BarProductsModel
import com.ipca.smartbar.bar.products.BarProductsRequests
import com.ipca.smartbar.databinding.FragmentAddProductBinding
import kotlinx.android.synthetic.main.fragment_add_product.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class AddProductFragment(
    private val productType: Int,
    val token: String?) : Fragment() {

    private var pickedPhoto: Uri? = null
    private var pickedBitMap: Bitmap? = null
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val product = (BarProductsModel("","", "",0.0,0,0))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        changeTitle()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextProductName = binding.editTextProductName
        val editTextProductPrice = binding.editTextNumberDecimalPrice
        val editTextProductStock = binding.editTextNumberStock
        val textViewUploadImage = binding.textViewUploadProductImage
        val buttonInsertProduct = binding.buttonConfirmProductAdd
        val buttonCancel = binding.buttonCancellProductAdd

        textViewUploadImage.setOnClickListener(){
            pickPhoto(this.requireView())
        }

        buttonCancel.setOnClickListener(){
           goToFragment()
        }

        buttonInsertProduct.setOnClickListener(){
            if(editTextProductName.text.isNotEmpty() &&
                editTextProductPrice.text.isNotEmpty() &&
                editTextProductStock.text.isNotEmpty() &&
                pickedBitMap != null) {
                val byteArrayPicture = convertBitmapToByteArray(pickedBitMap!!)
                product.picture = Base64.getEncoder().encodeToString(byteArrayPicture)
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

    private fun postProduct(product: BarProductsModel){
        BarProductsRequests.postProduct(lifecycleScope, token, product){
            when (it){
                "OK" -> {
                    Toast.makeText(activity,"PRODUTO INSERIDO!", Toast.LENGTH_SHORT).show()
                    goToFragment()
                }
                "REPEATED" -> Toast.makeText(activity, "NOME DO PRODUTO REPETIDO", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(activity, "ERRO NA INSERÇÃO DO PRODUTO", Toast.LENGTH_SHORT).show()
            }
        }
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

    private fun changeTitle() {
        val title = binding.textViewTitleAddProduct
        if(productType == 1) title.text = "INSERIR MENU"
        if(productType == 2) title.text = "INSERIR SNACK"
        if(productType == 3) title.text = "INSERIR BEBIDA QUENTE"
        if(productType == 4) title.text = "INSERIR BEBIDA FRIA"
    }

    private fun pickPhoto(view: View){
        if(ContextCompat.checkSelfPermission(this.requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){
            pickedPhoto = data.data
            if(pickedPhoto != null) {
                if(Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.requireContext().contentResolver, pickedPhoto!!)
                    pickedBitMap = ImageDecoder.decodeBitmap(source)
                    pickedBitMap = resizeBitmap(pickedBitMap!!, 500)
                    imageViewAddProduct.setImageBitmap(pickedBitMap)

                } else {
                    pickedBitMap = MediaStore.Images.Media.getBitmap(this.requireContext().contentResolver, pickedPhoto)
                    pickedBitMap = resizeBitmap(pickedBitMap!!, 500)
                    imageViewAddProduct.setImageBitmap(pickedBitMap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun resizeBitmap(source: Bitmap, maxLength: Int): Bitmap {
        try {
            if (source.height >= source.width) {
                if (source.height <= maxLength) return source

                val aspectRatio = source.width.toDouble() / source.height.toDouble()
                val targetWidth = (maxLength * aspectRatio).toInt()

                return Bitmap.createScaledBitmap(source, targetWidth, maxLength, false)
            } else {
                if (source.width <= maxLength) return source

                val aspectRatio = source.height.toDouble() / source.width.toDouble()
                val targetHeight = (maxLength * aspectRatio).toInt()

                return Bitmap.createScaledBitmap(source, maxLength, targetHeight, false)
            }
        } catch (e: Exception) { return source }
    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        var byteArrayImage: ByteArrayOutputStream? = null
        return try {
            byteArrayImage = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayImage)
            byteArrayImage.toByteArray()
        } finally {
            if (byteArrayImage != null) {
                try { byteArrayImage.close() }
                catch (e: IOException) { Toast.makeText(this.requireContext(), "ERRO NA CONVERSÃO DA IMAGEM", Toast.LENGTH_SHORT).show() }
            }
        }
    }
}

























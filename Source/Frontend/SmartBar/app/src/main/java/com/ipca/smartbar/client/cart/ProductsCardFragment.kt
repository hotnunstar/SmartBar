package com.ipca.smartbar.client.cart

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.client.cart.adapter.Adapter
import com.ipca.smartbar.client.products.Product
import com.ipca.smartbar.client.products.api.Repository
import com.ipca.smartbar.client.products.dataBase.AppDatabase
import com.ipca.smartbar.client.profile.ClientProfileActivity
import com.ipca.smartbar.databinding.FragmentProductsCardBinding
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime


class ProductsCardFragment(private val token:String?) : Fragment(){
    private val spinnerOptionBar = arrayOf("Bar 1", "Bar 2")
    private lateinit var binding: FragmentProductsCardBinding
    private lateinit var adapter: Adapter
    var products = ArrayList<Product>()
    var preco=0.0
    var bar : String=""
    var horario : String=""
    @RequiresApi(Build.VERSION_CODES.O)
    var localDateHour = LocalDateTime.now().hour
    @RequiresApi(Build.VERSION_CODES.O)
    var localDateMinute = LocalDateTime.now().minute
    @RequiresApi(Build.VERSION_CODES.O)
    private val spinnerOptionHorario = arrayListOf("${localDateHour} : ${localDateMinute}")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerBar()
        spinnerHoraPedido()
        retornaProdutosDb()
        binding.buttonConfirmarPedido.setOnClickListener {


            val listaFinal = CriaPair()
            var pedido = Pedido(listProducts = listaFinal, preco = preco, bar = bar)
            if(listaFinal.size==0)
            {
                Toast.makeText(context, "Escolher produtos para efetuar a compra", Toast.LENGTH_SHORT).show()
            }
            var result:Pair<String,Boolean>
            lifecycleScope.launch(Dispatchers.IO)
            {

                  result = Repository.confirmarPedido(pedido,token)
                    //controlaResult(result)
            }

        }

    }

    fun retornaProdutosDb() {
        AppDatabase.getDatabase(requireContext())?.productDao()?.getAll()
            ?.observe(viewLifecycleOwner, Observer(::bindValues))
    }

    private fun loadList(product: ArrayList<Product>) {
        val list = binding.lvProducts
        valorTotal(product)
        val context: Context = this.context as Context
        adapter = Adapter(context, product,this)
        list.adapter = adapter

    }

    private fun bindValues(list: List<Product>) {
        val lista: ArrayList<Product> = list as ArrayList<Product>
        products = lista
        loadList(lista)
        adapter.notifyDataSetChanged()

    }

     fun deleteProduct(product:Product)
    {
        lifecycleScope.launch(Dispatchers.IO)
        {
            AppDatabase.getDatabase(requireContext())?.productDao()?.delete(product)
        }

    }
    fun spinnerBar()
    {
        val context = context as Context
        var spinnerBar = binding.spinnerChooseBar
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerOptionHorario)
        spinnerBar.adapter = arrayAdapter

        spinnerBar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bar = spinnerOptionBar[position]
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onNothingSelected(parent: AdapterView<*>?) {
                bar = spinnerOptionBar[0]
            }
        }
    }
    fun spinnerHoraPedido()
    {
        val context = context as Context
        var spinnerHorario = binding.spinnerHorario
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerOptionBar)
        spinnerHorario.adapter = arrayAdapter

        spinnerHorario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                horario = spinnerOptionHorario[position]
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onNothingSelected(parent: AdapterView<*>?) {
                horario = spinnerOptionHorario[0]
            }
        }
    }
    fun valorTotal(product: ArrayList<Product>)
    {
        var aux =0.0
        for(item in product)
        {
            aux += item.preco * item.quantity

        }
        preco=aux
        binding.textViewTotalPrice.text = aux.toString()
    }
    fun apdateProduct(product:Product, products2: ArrayList<Product>){
    lifecycleScope.launch(Dispatchers.IO)
    {
        AppDatabase.getDatabase(requireContext())?.productDao()?.insertAll(product)
        products = products2
    }
}

    fun CriaPair(): List<ProductPedido>
    {
        val listPair = ArrayList<ProductPedido>()

        for(item in products)
        {

            listPair.add(ProductPedido(item.id,item.quantity))
        }
        return listPair
    }
    fun controlaResult(item: Pair<String, Boolean>)
    {
        if(item.second==false)
        {
            Toast.makeText(context, "correu mal!", Toast.LENGTH_SHORT).show()

        }
    }




}




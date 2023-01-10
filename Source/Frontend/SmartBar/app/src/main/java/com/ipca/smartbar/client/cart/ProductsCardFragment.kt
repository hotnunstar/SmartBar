package com.ipca.smartbar.client.cart

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.checkUserId
import com.ipca.smartbar.client.cart.adapter.Adapter
import com.ipca.smartbar.client.products.Product
import com.ipca.smartbar.client.products.api.Repository
import com.ipca.smartbar.client.products.dataBase.AppDatabase
import com.ipca.smartbar.databinding.FragmentProductsCardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import kotlin.math.roundToInt


class ProductsCardFragment(private val token:String?) : Fragment(){
    private val spinnerOptionBar = arrayOf("Bar 1", "Bar 2")
    private lateinit var binding: FragmentProductsCardBinding
    private lateinit var adapter: Adapter
    var products = ArrayList<Product>()
    var preco=0.0
    var horasPedido :String=""
    var state:Int =1
    var idRequest:String=""
    var firebase:String=""
    var bar : String=""
    var controller = true
    var dateRequest:String=""
    var horario : String=""
    var idCliente = checkUserId(token!!)
    @RequiresApi(Build.VERSION_CODES.O)
    var localDateHour = LocalDateTime.now().hour
    @RequiresApi(Build.VERSION_CODES.O)
    var localDateMinute = LocalDateTime.now().minute
    @RequiresApi(Build.VERSION_CODES.O)
    private val spinnerOptionHorario = popularSpinner(localDateHour,localDateMinute)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerBar()
        spinnerHoraPedido()
        retornaProdutosDb()
        binding.buttonConfirmarPedido.setOnClickListener {


            val listaFinal = CriaPair()
            var pedido = Pedido(
                listProducts = listaFinal,
                preco = preco,
                bar = bar,
                idCliente = idCliente,
                state = state,
                id = idRequest,
                firebaseToken = firebase,
                horas = horasPedido,
                dateRequest = dateRequest
            )
            if(horasPedido !="") {

                if (listaFinal.size == 0) {
                    Toast.makeText(
                        context,
                        "Escolher produtos para efetuar a compra",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    var result: Pair<String, Boolean>
                    lifecycleScope.launch(Dispatchers.IO)
                    {

                        result = Repository.confirmarPedido(pedido, token)
                        controller = controlaResult(result)
                        if (controller) deleteProductFinal(products)
                    }
                }
            } else{
                Toast.makeText(
                    context,
                    "Preenche as horas guloso",
                    Toast.LENGTH_SHORT
                ).show()
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
    fun deleteProductFinal(products:ArrayList<Product>)
    {
        for(product in products)
        {
            deleteProduct(product)
        }
    }
     fun deleteProduct(product:Product)
    {
        lifecycleScope.launch(Dispatchers.IO)
        {
            AppDatabase.getDatabase(requireContext())?.productDao()?.delete(product)
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun spinnerBar()
    {
        val context = context as Context
        var spinnerBar = binding.spinnerChooseBar
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerOptionBar )
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
    @RequiresApi(Build.VERSION_CODES.O)
    fun spinnerHoraPedido()
    {
        val context = context as Context
        var spinnerHorario = binding.spinnerHorario
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,spinnerOptionHorario)
        spinnerHorario.adapter = arrayAdapter

        spinnerHorario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                horario = spinnerOptionHorario[position]
                horasPedido=horario
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onNothingSelected(parent: AdapterView<*>?) {
                horario = spinnerOptionHorario[0]
            }
        }
    }
    fun popularSpinner(hora:Int, minuto:Int):ArrayList<String>
    {
        var listaHoras = ArrayList<String>()
        val intervalo = minuto / 15
        var minutoInicial =0
        if(intervalo<1){
            minutoInicial=15
        }else if(intervalo<2)
        {
            minutoInicial=30
        }else if(intervalo<3){
            minutoInicial=45
        }
        var horaFechar=20
        if(hora < horaFechar)
        {
            for(i in hora..horaFechar)
            {
                for(a in minutoInicial..45 step 15)
                {
                    listaHoras.add("$i:$a")
                }
                listaHoras.add((i+1).toString()+":00")
                minutoInicial=15
            }
        }
        return listaHoras
    }
    fun valorTotal(product: ArrayList<Product>)
    {
        var aux =0.0
        for(item in product)
        {
            aux += item.preco * item.quantity

        }
        preco=aux
        val roundoff = (preco*100).roundToInt().toDouble() / 100
        binding.textViewTotalPrice.text =  roundoff.toString()
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
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun controlaResult(item: Pair<String, Boolean>) : Boolean
    {
        if(item.second==false)
        {
            withContext(Dispatchers.Main)
            {
                Toast.makeText(context, item.first, Toast.LENGTH_SHORT).show()
            }
            return false
        } else if(item.second==true)
        {
            withContext(Dispatchers.Main)
            {
                Toast.makeText(context, "Pedido efetuado com sucesso \n aguardar confirmação", Toast.LENGTH_SHORT).show()
            }
            return true
        }
        return true
    }




}




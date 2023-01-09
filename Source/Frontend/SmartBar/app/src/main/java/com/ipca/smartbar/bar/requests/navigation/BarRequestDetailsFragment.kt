package com.ipca.smartbar.bar.requests.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.products.BarProductsModel
import com.ipca.smartbar.bar.requests.BarRequestsModel
import com.ipca.smartbar.bar.requests.BarRequestsRequests
import com.ipca.smartbar.databinding.FragmentBarRequestDetailsBinding

class BarRequestDetailsFragment(private val token: String?,
                             private val request: BarRequestsModel) : Fragment() {

    private var _binding: FragmentBarRequestDetailsBinding? = null
    private val binding get() = _binding!!
    private val adapter = RequestDetailsAdapter()
    var products: ArrayList<BarProductsModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarRequestDetailsBinding.inflate(inflater, container, false)
        binding.viewProgressBarBarRequestDetails.visibility = View.VISIBLE
        binding.progressBarBarRequestDetails.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textViewRequestID = binding.textViewRequestID
        val textViewRequestDate = binding.textViewRequestDate
        val textViewRequestPickUpHour = binding.textViewRequestPickUpHour
        val textViewRequestTotalValue = binding.textViewRequestTotalValue
        val buttonConfirmRequest = binding.buttonConfirmProductRequest
        val buttonCancelRequest = binding.buttonCancellProductRequest

        textViewRequestID.append(" ${request.idRequest}")
        textViewRequestDate.append(" ${request.dateRequest}")
        textViewRequestPickUpHour.append(" ${request.horas}h")
        textViewRequestTotalValue.append(" ${request.value}€")

        val arraySize = request.productAndQuantity!!.size
        for (index in 0 until arraySize)
        {
            BarRequestsRequests.getProductByID(lifecycleScope, token, request.productAndQuantity!![index].idProduct){
                if (it != null) {
                    products.add(it)
                    adapter.notifyDataSetChanged()
                    binding.listViewProducts.adapter = adapter
                    binding.viewProgressBarBarRequestDetails.visibility = View.GONE
                    binding.progressBarBarRequestDetails.visibility = View.GONE
                }
            }
        }
        if(request.state == 1)
        {
            buttonConfirmRequest.text = "Aceitar pedido"
            buttonCancelRequest.text = "Recusar pedido"
        }
        if(request.state == 2){
            buttonConfirmRequest.text = "Pronto para levantamento!"
            buttonCancelRequest.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class RequestDetailsAdapter: BaseAdapter(){
        override fun getCount(): Int {
            return request.productAndQuantity!!.size
        }

        override fun getItem(position: Int): Any {
            return request.productAndQuantity!![position]
        }

        override fun getItemId(position: Int): Long {
            return 0L
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_request_products_line, parent, false)
            val textViewProductName = rowView.findViewById<TextView>(R.id.textViewProductLineName)
            val textViewProductQuantity = rowView.findViewById<TextView>(R.id.textViewProductLineQuantity)
            val textViewTotalPrice = rowView.findViewById<TextView>(R.id.textViewProductLinePrice)
            val product = products[position]
            val productTotalPrice = request.productAndQuantity!![position].quantity * product.price
            textViewProductName.append(" ${product.name}")
            textViewProductQuantity.append(" ${request.productAndQuantity?.get(position)!!.quantity}")
            textViewTotalPrice.text = "${productTotalPrice}€"

            return rowView
        }
    }
}
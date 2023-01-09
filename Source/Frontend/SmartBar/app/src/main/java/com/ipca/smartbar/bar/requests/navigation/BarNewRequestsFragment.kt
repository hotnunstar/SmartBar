package com.ipca.smartbar.bar.requests.navigation

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.requests.BarRequestsModel
import com.ipca.smartbar.bar.requests.BarRequestsRequests
import com.ipca.smartbar.databinding.FragmentBarRequestsBinding

class BarNewRequestsFragment(private val token: String?) : Fragment() {

    private var _binding: FragmentBarRequestsBinding? = null
    private val binding get() = _binding!!
    private val adapter = RequestsAdapter()
    private var requests = ArrayList<BarRequestsModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarRequestsBinding.inflate(inflater, container, false)
        binding.viewProgressBarBarRequests.visibility = View.VISIBLE
        binding.progressBarBarRequests.visibility = View.VISIBLE
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BarRequestsRequests.getRequests(lifecycleScope, token, 1){
            requests = it
            adapter.notifyDataSetChanged()
        }

        val buttonPageReload = binding.buttonUpdatePage
        buttonPageReload.setOnClickListener(){
            val fragment = BarNewRequestsFragment(token)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewBarRequests, fragment)
            transaction.commit()
        }

        binding.listViewBarRequests.adapter = adapter
        binding.viewProgressBarBarRequests.visibility = View.GONE
        binding.progressBarBarRequests.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class RequestsAdapter: BaseAdapter(){
        override fun getCount(): Int {
            return requests.size
        }

        override fun getItem(position: Int): Any {
            return requests[position]
        }

        override fun getItemId(position: Int): Long {
            return 0L
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_requests, parent, false)
            val textViewRequestId = rowView.findViewById<TextView>(R.id.textViewRowRequestID)
            val textViewRequestDate = rowView.findViewById<TextView>(R.id.textViewRowRequestDate)
            val textViewRequestHour = rowView.findViewById<TextView>(R.id.textViewRowRequestHour)
            val textViewRequestState = rowView.findViewById<TextView>(R.id.textViewRowRequestState)
            val buttonCheckRequest = rowView.findViewById<Button>(R.id.buttonCheckRequest)
            val request = requests[position]

            textViewRequestId.append(" ${request.idRequest}")
            textViewRequestDate.append(" ${request.dateRequest}")
            textViewRequestHour.append(" ${request.horas}h")
            textViewRequestState.append(" Recebido")

            buttonCheckRequest.setOnClickListener(){
                val fragment = BarRequestDetailsFragment(token, request)
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerViewBarRequests, fragment)
                transaction.commit()
            }
            return rowView
        }
    }
}
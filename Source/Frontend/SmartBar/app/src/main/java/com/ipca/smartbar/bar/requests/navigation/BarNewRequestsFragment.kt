package com.ipca.smartbar.bar.requests.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.lifecycle.lifecycleScope
import com.ipca.smartbar.R
import com.ipca.smartbar.bar.requests.BarRequestsModel
import com.ipca.smartbar.bar.requests.BarRequestsRequests
import com.ipca.smartbar.databinding.FragmentBarProductsBinding
import com.ipca.smartbar.databinding.FragmentBarRequestsBinding

class BarNewRequestsFragment(private val token: String?) : Fragment() {

    private var _binding: FragmentBarRequestsBinding? = null
    private val binding get() = _binding!!
    private val adapter = RequestsAdapter()
    private var requests = ArrayList<BarRequestsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarRequestsBinding.inflate(inflater, container, false)
        binding.viewProgressBarBarRequests.visibility = View.VISIBLE
        binding.progressBarBarRequests.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BarRequestsRequests.getRequests(lifecycleScope, token, 1){

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class RequestsAdapter: BaseAdapter(){
        override fun getCount(): Int {
            TODO("Not yet implemented")
        }

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemId(position: Int): Long {
            TODO("Not yet implemented")
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            TODO("Not yet implemented")
        }
    }
}
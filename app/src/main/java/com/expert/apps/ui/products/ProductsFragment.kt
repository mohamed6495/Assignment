package com.expert.apps.ui.products

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expert.apps.R
import com.expert.apps.databinding.FragmentProductBinding
import com.expert.apps.ui.products.viewModel.ProductViewModel
import com.expert.apps.utils.Constants
import com.expert.apps.utils.Resource
import com.expert.apps.utils.checkNetwork.checkConnection
import com.expert.apps.utils.progressLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_product) {

    private var binding: FragmentProductBinding? = null
    private val viewModel: ProductViewModel by viewModels()
    @set:Inject
    var adapter: ProductAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        if (checkConnection.getInstance()!!.checkNetWork(activity)) {
            viewModel.getProduct()
        } else {
              try_again.visibility = View.VISIBLE
                binding?.recyclerView?.visibility = View.GONE
        }

        subscribeToUI()


    }

    /**
     * this method is used to setup the basics for the recycler views
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupRecyclerView() {

        val sdf = SimpleDateFormat("yyyyMdd")
        val currentDate = sdf.format(Date())

        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.adapter = adapter
        adapter?.setOnItemClickListener {
            viewModel.insertCompetitions(it)
            ////////// here add 3 day + today and i will   check if this date +3 =today
            it.data=currentDate.toInt().plus(3)
            Toast.makeText(context, R.string.Successfully, Toast.LENGTH_SHORT).show()

        }
    }

    private fun subscribeToUI() {
        viewModel.productLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        adapter?.differ?.submitList(newsResponse.products)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                       try_again.visibility = View.VISIBLE
                        binding?.recyclerView?.visibility = View.GONE
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        progressLoading.HideProgress()
    }

    private fun showProgressBar() {
        progressLoading.CreateProgress(activity)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
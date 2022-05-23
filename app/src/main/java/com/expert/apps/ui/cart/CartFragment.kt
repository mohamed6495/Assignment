package com.expert.apps.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expert.apps.R
import com.expert.apps.databinding.FragmentCartBinding
import com.expert.apps.ui.products.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cart.*
import java.text.SimpleDateFormat
import java.util.*

import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private var binding: FragmentCartBinding? = null
    private val viewModel: ProductViewModel by viewModels()
    var total: Int = 0
    var currentDate=0
    @set:Inject
    var adapter: CartAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeToUI()
        val sdf = SimpleDateFormat("yyyyMdd")
         currentDate = sdf.format(Date()).toInt()
    }

    /**
     * this method is used to setup the basics for the recycler views
     */
    private fun setupRecyclerView() {
        binding?.recyclerViewCart?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerViewCart?.adapter = adapter
        adapter?.setOnItemClickListener {
            viewModel.insertCompetitions(it)
            Toast.makeText(context, R.string.Successfully, Toast.LENGTH_SHORT).show()
        }
        adapter?.setOnItemRemoveListener {
            viewModel.removeCity(it)
            Toast.makeText(context, R.string.delete, Toast.LENGTH_SHORT).show()
        }
    }

    private fun subscribeToUI() {

        viewModel.getProductsCash().observe(viewLifecycleOwner) { response ->
            response.let {
                if (!response.isNullOrEmpty()) {
                    adapter?.differ?.submitList(it)
                    total=0
                    for (i in it) {
                        total += i.quantity * i.price!!

                        if (i.data!! <=currentDate){
                            viewModel.removeCity(i)
                        }
                    }
                    sub_total.text = "$total $"
                }else{
                    binding?.lineEmpty?.visibility=View.VISIBLE
                    binding?.scrollView?.visibility=View.GONE
                    binding?.dataEmpty?.txtEmpty?.text=getString(R.string.empty)

                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
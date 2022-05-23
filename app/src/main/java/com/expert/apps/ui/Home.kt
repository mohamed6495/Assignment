package com.expert.apps.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.expert.apps.R
import com.expert.apps.databinding.HomeActivityBinding
import com.expert.apps.ui.products.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private val viewModel: ProductViewModel by viewModels()
    private var totalQuantity:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_home_activity)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_cart))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        subscribeToUI()
    }

    private fun subscribeToUI() {
        viewModel.getProductsCash().observe(this) { response ->
            response.let {
                if (!response.isNullOrEmpty()) {
                    totalQuantity=0
                    for (i in it) {
                        totalQuantity += i.quantity!!
                    }
                    binding.navView .getOrCreateBadge(R.id.navigation_cart).number = totalQuantity

                }
            }
        }
    }
}
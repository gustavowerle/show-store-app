package com.udacity.shoestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.shoe.ShoeListFragmentDirections
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(
            this,
            navController,
            binding.drawerLayout
        )
        NavigationUI.setupWithNavController(binding.navView, navController)
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, _: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                supportActionBar?.show()
            } else {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                supportActionBar?.hide()
            }
        }
        viewModel.isLoggedIn.observe(this, Observer {
            if (!it && !viewModel.isLogging)
                navController.navigate(ShoeListFragmentDirections.actionShoeListFragmentToLoginFragment())
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }
}

package com.udacity.shoestore

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.screens.shoelisting.ShoeListFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        Timber.plant(Timber.DebugTree())

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

        this.toolbar.title = ""

        this.toolbar.inflateMenu(R.menu.overflow_menu)

    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId){
            R.id.logoutAction -> {

                // Set Login bool to false
                val loginSP = getSharedPreferences(getString(R.string.login_pref), Context.MODE_PRIVATE)
                var loginEdit = loginSP?.edit()
                loginEdit?.putBoolean(getString(R.string.login_bool), false)
                loginEdit?.commit()

                navigateToLoginFragment()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun navigateToLoginFragment(){
        navController.navigate(R.id.loginFragment)
    }

}



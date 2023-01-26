package com.nandaiqbalh.newsapp.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nandaiqbalh.newsapp.R

class HomeActivity : AppCompatActivity() {

	private lateinit var navController: NavController
	private lateinit var bottomNav: BottomNavigationView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		supportActionBar?.hide()
		setContentView(R.layout.activity_home)

		val navHostFragment = supportFragmentManager
			.findFragmentById(R.id.fragmentContainerViewHome) as NavHostFragment

		navController = navHostFragment.navController

		bottomNav = findViewById(R.id.bottom_navigation_user)

		val appBarConfiguration = AppBarConfiguration.Builder(
			R.id.breakingNewsFragment,
			R.id.searchFragment,
			R.id.savedFragment
		).build()

		bottomNav.itemIconTintList = null

		setupActionBarWithNavController(navController, appBarConfiguration)

		bottomNav.setupWithNavController(navController)
		navController.addOnDestinationChangedListener { _, destination, _ ->
			when (destination.id) {
				R.id.articleFragment-> {
					hideBottomNav(true)
				}
				else -> hideBottomNav(false)
			}
		}
	}

	private fun hideBottomNav(hide: Boolean) {
		if (hide) {
			bottomNav.visibility = View.GONE
		} else {
			bottomNav.visibility = View.VISIBLE
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp() || super.onSupportNavigateUp()
	}

}
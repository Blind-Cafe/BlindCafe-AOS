package com.abouttime.blindcafe.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.constants.LogTag.FCM
import com.abouttime.blindcafe.common.constants.Url
import com.abouttime.blindcafe.databinding.ActivityNavHostBinding
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.ext.android.viewModel

class NavHostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavHostBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val viewModel: NavHostViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BlindCafe)
        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)




        initNavController()
        observeData()

    }
    private fun initNavController() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun observeData() {
        viewModel.navDirectionEvent.observe(this) { directions ->
            Log.d("asdf", "NavHostActivity 의 observeData")
            navController.navigate(directions)
        }
    }

    internal fun moveToDirections(directions: NavDirections) {
        navController.navigate(directions)
    }


}
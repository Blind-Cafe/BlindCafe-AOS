package com.abouttime.blindcafe.presentation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
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
import android.widget.EditText

import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.recyclerview.widget.ItemTouchHelper.UP


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

    override fun onBackPressed() {
        super.onBackPressed()


    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.d("asdf", "before ${event.action}  ")

        if (event.action == MotionEvent.ACTION_UP && event.downTime > 5000) {
            Log.d("asdf", "after ${event.action}  downTime ${event.downTime}")

            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt()) ) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }








}
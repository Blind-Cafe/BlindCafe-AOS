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
import android.view.WindowManager
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

        if (event.action == MotionEvent.ACTION_UP) {
            val v: View? = currentFocus

            //Log.d("asdf", " ->\nView\n top : ${v?.top}, bottom : ${v?.bottom}, height : ${v?.height}\n left : ${v?.left}, right : ${v?.right}, width : ${v?.width}")
            //Log.d("asdf", " ->\nView\n x : ${v?.x}, y : ${v?.y}\n pivotX : ${v?.pivotX}, pivotY : ${v?.pivotY}")
            //Log.d("asdf", " ->\nEvent\n x : ${event.x}, y : ${event.y}\n rawX : ${event.rawX}, rawY : ${event.rawY}\n xPrecision : ${event.xPrecision}, yPrecision : ${event.yPrecision}")

            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                //v.getFocusedRect(outRect)

                Log.e("asdf", "->\noutRect.top ${outRect.top}\nevent.rawY ${event.rawY}\noutRect.bottom ${outRect.bottom}\nevent.rawX ${event.rawX}")
                if (outRect.top > event.rawY.toInt()) {
                //if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt()) ) {
                    v.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }








}
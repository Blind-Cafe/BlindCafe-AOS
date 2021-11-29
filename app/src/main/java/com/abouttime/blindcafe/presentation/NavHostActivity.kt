package com.abouttime.blindcafe.presentation

import android.app.AlertDialog
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.ActivityNavHostBinding
import org.koin.android.viewmodel.ext.android.viewModel

import android.view.MotionEvent
import android.view.View


class NavHostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavHostBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val viewModel: NavHostViewModel by viewModel()

    private lateinit var  loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BlindCafe)
        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavController()

        initLoadingDialog()
        observeLoadingEvent()

    }
    private fun initNavController() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }


    private fun initLoadingDialog() {
        val b = AlertDialog.Builder(this)
        val i = this.layoutInflater
        b.setView(i.inflate(R.layout.dialog_fragment_loading, null))
        b.setCancelable(false)
        loadingDialog = b.create()
        loadingDialog.window?.setDimAmount(0f)
        loadingDialog.window?.setBackgroundDrawableResource(R.color.transparent)

    }


    private fun observeLoadingEvent() {
        GlobalLiveData.loadingEvent.observe(this) {
            if (it) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
            }
        }
    }

    private fun showLoadingDialog() {
        loadingDialog.show()
    }

    protected fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }



    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_UP) {
            val v: View? = currentFocus

            //Log.d("asdf", " ->\nView\n top : ${v?.top}, bottom : ${v?.bottom}, height : ${v?.height}\n left : ${v?.left}, right : ${v?.right}, width : ${v?.width}")
            //Log.d("asdf", " ->\nView\n x : ${v?.x}, y : ${v?.y}\n pivotX : ${v?.pivotX}, pivotY : ${v?.pivotY}")
            //Log.d("asdf", " ->\nEvent\n x : ${event.x}, y : ${event.y}\n rawX : ${event.rawX}, rawY : ${event.rawY}\n xPrecision : ${event.xPrecision}, yPrecision : ${event.yPrecision}")

            if (v?.id == R.id.et_message_input) {
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
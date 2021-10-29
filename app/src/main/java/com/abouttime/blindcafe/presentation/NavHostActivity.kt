package com.abouttime.blindcafe.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abouttime.blindcafe.R

class NavHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BlindCafe)
        setContentView(R.layout.activity_nav_host)
    }
}
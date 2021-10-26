package com.abouttime.blindcafe.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.presentation.onboarding.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            )
        )
    }
}
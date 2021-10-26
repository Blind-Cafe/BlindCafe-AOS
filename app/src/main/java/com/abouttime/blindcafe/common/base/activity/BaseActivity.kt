package com.abouttime.blindcafe.common.base.activity

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BaseActivity : AppCompatActivity() {



    // Show Toast
    internal fun showToast(resId: Int) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
    }


}

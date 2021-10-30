package com.abouttime.blindcafe.common.base.fragment

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.abouttime.blindcafe.presentation.NavHostActivity
import com.abouttime.blindcafe.presentation.NavHostViewModel
import com.abouttime.blindcafe.presentation.onboarding.rule.RuleFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

open class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    val activityViewModel: NavHostViewModel by viewModel()
    val parentActivity: NavHostActivity by lazy {
        activity as NavHostActivity
    }

    fun moveToDirections(directions: NavDirections) {
        Log.d("asdf", "BaseFragment 의 moveToDriections")
        parentActivity.moveToDirections(directions)
    }


    fun showToast(resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }
}
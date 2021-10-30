package com.abouttime.blindcafe.presentation.onboarding.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.presentation.NavHostViewModel

class SplashFragment: Fragment(R.layout.fragment_splash) {

    private val activityViewModel: NavHostViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO 여기서 jwt 여부 확인해서 main 화면으로 갈지, 온보딩 화면으로 갈지 결정

        activityViewModel.navDirectionEvent.value = SplashFragmentDirections.actionSplashFragmentToRuleFragment()

    }


}
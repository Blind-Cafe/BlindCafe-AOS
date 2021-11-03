package com.abouttime.blindcafe.presentation.main.my_page

import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.R
import org.koin.android.viewmodel.ext.android.viewModel

class MyPageFragment: BaseFragment<MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModel()
}
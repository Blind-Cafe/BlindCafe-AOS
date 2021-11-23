package com.abouttime.blindcafe.presentation.main.my_page.setting.account_delete.complete

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentAccountDeleteCompleteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AccountDeleteCompleteFragment: BaseFragment<AccountDeleteCompleteViewModel>(R.layout.fragment_account_delete_complete) {
    override val viewModel: AccountDeleteCompleteViewModel by viewModel()
    private var binding: FragmentAccountDeleteCompleteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentAccountDeleteCompleteBinding = FragmentAccountDeleteCompleteBinding.bind(view)
        binding = fragmentAccountDeleteCompleteBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

    }


}
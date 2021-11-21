package com.abouttime.blindcafe.presentation.main.my_page.setting.account_delete

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentAccountDeleteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AccountDeleteFragment: BaseFragment<AccountDeleteViewModel>(R.layout.fragment_account_delete) {
    override val viewModel: AccountDeleteViewModel by viewModel()
    private var binding: FragmentAccountDeleteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAccountDeleteBinding = FragmentAccountDeleteBinding.bind(view)
        binding = fragmentAccountDeleteBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

    }
}
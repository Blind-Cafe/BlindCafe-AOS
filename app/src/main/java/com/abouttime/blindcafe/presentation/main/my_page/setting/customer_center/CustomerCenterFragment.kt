package com.abouttime.blindcafe.presentation.main.my_page.setting.customer_center

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentCustomerCenterBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CustomerCenterFragment: BaseFragment<CustomerCenterViewModel>(R.layout.fragment_customer_center) {
    override val viewModel: CustomerCenterViewModel by viewModel()
    private var binding: FragmentCustomerCenterBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentCustomerCenterBinding = FragmentCustomerCenterBinding.bind(view)
        binding = fragmentCustomerCenterBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
    }
}
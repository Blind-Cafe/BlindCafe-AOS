package com.abouttime.blindcafe.presentation.main.my_page.edit.interest

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentInterestEditBinding
import org.koin.android.viewmodel.ext.android.viewModel

class InterestEditFragment: BaseFragment<InterestEditViewModel>(R.layout.fragment_interest_edit) {
    override val viewModel: InterestEditViewModel by viewModel()
    private var binding: FragmentInterestEditBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentInterestEditBinding = FragmentInterestEditBinding.bind(view)
        binding = fragmentInterestEditBinding

    }
}
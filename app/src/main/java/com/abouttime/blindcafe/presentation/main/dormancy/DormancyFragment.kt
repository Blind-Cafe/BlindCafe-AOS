package com.abouttime.blindcafe.presentation.main.dormancy

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.PreferenceKey.NICKNAME
import com.abouttime.blindcafe.databinding.FragmentDormancyBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DormancyFragment: BaseFragment<DormancyViewModel>(R.layout.fragment_dormancy) {
    override val viewModel: DormancyViewModel by viewModel()
    private var binding: FragmentDormancyBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentDormancyBinding = FragmentDormancyBinding.bind(view)
        binding = fragmentDormancyBinding

        initTextView()
    }
    private fun initTextView() {
        binding?.tvDescription?.text = getString(R.string.dormancy_description).format(getStringData(NICKNAME))
    }
}
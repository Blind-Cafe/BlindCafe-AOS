package com.abouttime.blindcafe.presentation.profile_exchange.accept.profile_image

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import com.abouttime.blindcafe.databinding.DialogFragmentProfileImageBinding
import com.abouttime.blindcafe.presentation.onboarding.rule.RuleVpAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class AcceptImageDialogFragment: BaseDialogFragment<AcceptImageViewModel>(R.layout.dialog_fragment_profile_image) {
    override val viewModel: AcceptImageViewModel by viewModel()
    private var binding: DialogFragmentProfileImageBinding? = null
    private val args: AcceptImageDialogFragmentArgs by navArgs()
    private lateinit var adapter: AcceptImageVpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent);

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogFragmentProfileImageBinding = DialogFragmentProfileImageBinding.bind(view)
        binding = dialogFragmentProfileImageBinding






        initArgs()
        observeImageUrlsData()
        initViewPager2(dialogFragmentProfileImageBinding)
        initBackButton()

    }

    private fun initArgs() {
        val partnerUserId = args.partnerUserId
        viewModel?.getProfileImages(partnerUserId)

    }
    private fun observeImageUrlsData() {
        viewModel?.imageUrls?.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    private fun initViewPager2(dialogFragmentProfileImageBinding: DialogFragmentProfileImageBinding) = with(dialogFragmentProfileImageBinding) {
        adapter = AcceptImageVpAdapter()
        vpImageContainer.adapter = adapter
        diIndicator.setViewPager2(vpImageContainer)
    }

    private fun initBackButton() {
        binding?.ivBack?.setOnClickListener {
            popOneDirections()
        }
    }

}
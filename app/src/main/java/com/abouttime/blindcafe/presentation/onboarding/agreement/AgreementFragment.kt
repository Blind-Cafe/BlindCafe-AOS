package com.abouttime.blindcafe.presentation.onboarding.agreement

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentAgreementBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AgreementFragment : BaseFragment<AgreementViewModel>(R.layout.fragment_agreement) {
    private var binding: FragmentAgreementBinding? = null
    override val viewModel: AgreementViewModel by viewModel()

    private lateinit var checkImageViews: List<ImageView>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAgreementBinding = FragmentAgreementBinding.bind(view)
        binding = fragmentAgreementBinding
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this



        //initCheckButtons(fragmentAgreementBinding)
        //initNextButton(fragmentAgreementBinding)
    }

    private fun initCheckButtons(fragmentAgreementBinding: FragmentAgreementBinding) =
        with(fragmentAgreementBinding) {
            checkImageViews = listOf(
                ivCheck1,
                ivCheck2,
                ivCheck3,
                ivCheck4
            )
//            checkImageViews.forEachIndexed { i, ivCheck ->
//                ivCheck.setOnClickListener {
//                    if (viewModel!!.checks[i]) {
//                        viewModel!!.checks[i] = false
//                        ivCheck.setColorFilter(resources.getColor(R.color.check_color, null))
//                    } else {
//                        viewModel!!.checks[i] = true
//                        ivCheck.setColorFilter(resources.getColor(R.color.checked_color, null))
//                    }
//                    viewModel?.isAllChecked()
//                }
//            }
        }


    private fun initNextButton(fragmentAgreementBinding: FragmentAgreementBinding) =
        with(fragmentAgreementBinding) {
            tvNext.setOnClickListener {
                viewModel?.onClickNextButton()
            }
        }


}
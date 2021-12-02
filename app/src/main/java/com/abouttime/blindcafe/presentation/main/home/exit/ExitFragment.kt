package com.abouttime.blindcafe.presentation.main.home.exit

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentExitBinding
import com.abouttime.blindcafe.presentation.common.confirm.ConfirmDialogFragmentArgs
import org.koin.android.viewmodel.ext.android.viewModel

class ExitFragment: BaseFragment<ExitViewModel>(R.layout.fragment_exit) {
    override val viewModel: ExitViewModel by viewModel()
    private var binding: FragmentExitBinding? = null
    val args: ExitFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExitBinding = FragmentExitBinding.bind(view)
        binding = fragmentExitBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initArgs()


    }
    private fun initArgs() {
        val isAttacker = args.isAttacker
        val isReport = args.isReport
        val title = args.title

        if (isAttacker) {
            handleAttacker(isReport, title)
        } else {
            handleVictim(isReport, title)
        }
    }
    private fun handleAttacker(isReport: Boolean, title: String) {
        binding?.let { b ->
            with(b) {
                if (!isReport) {
                    tvTitleAttacker.isGone = false
                    tvTitleVictim.isGone = true
                    tvTitleAttacker.text = title
                } else {
                    tvTitleAttacker.isGone = true
                    tvTitleVictim.isGone = false
                    tvTitleVictim.text = title
                }
            }
        }
    }
    private fun handleVictim(isReport: Boolean, title: String) {
        binding?.let { b ->
            with(b) {
                if (isReport) {
                    tvTitleAttacker.isGone = false
                    tvTitleVictim.isGone = true
                    tvTitleAttacker.text = title
                } else {
                    tvTitleAttacker.isGone = true
                    tvTitleVictim.isGone = false
                    val start = title.indexOf("[")
                    val end = title.indexOf("]")
                    val builder = SpannableStringBuilder(title.replace("[", " ").replace("]", " "))
                    builder.setSpan(ForegroundColorSpan(getColorByResId(R.color.main)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    b.tvTitleVictim.append(builder)
                }
            }
        }

    }

}
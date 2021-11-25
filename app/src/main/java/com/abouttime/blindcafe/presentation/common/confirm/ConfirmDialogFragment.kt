package com.abouttime.blindcafe.presentation.common.confirm

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import com.abouttime.blindcafe.common.constants.NavigationKey.CONFIRM_MATCHING_CANCEL
import com.abouttime.blindcafe.common.constants.NavigationKey.CONFIRM_NO
import com.abouttime.blindcafe.common.constants.PreferenceKey.NICKNAME
import com.abouttime.blindcafe.databinding.DialogFragmentConfirmBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ConfirmDialogFragment :
    BaseDialogFragment<ConfirmViewModel>(R.layout.dialog_fragment_confirm) {
    val args: ConfirmDialogFragmentArgs by navArgs()


    override val viewModel: ConfirmViewModel by viewModel()
    private var binding: DialogFragmentConfirmBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogFragmentConfirmBinding = DialogFragmentConfirmBinding.bind(view)
        binding = dialogFragmentConfirmBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        isCancelable = false

        initViews(dialogFragmentConfirmBinding)
        bindData()
    }

    private fun initViews(dialogFragmentConfirmBinding: DialogFragmentConfirmBinding) =
        with(dialogFragmentConfirmBinding) {
            tvTitle.text = args.title ?: ""
            tvSubtitle.text = args.subtitle ?: ""
            tvNo.text = args.no ?: "취소"
            tvYes.text = args.yes ?: "확인"

        }

    private fun bindData() {
        when (args.id) {
            R.string.home_matching_cancel_title -> {
                handleHomeMatchingCancel()
            }
            R.string.profile_dismiss_confirm_title -> {
                handleProfileDismiss()
            }
            R.string.logout_confirm_title -> {
                handleLogout()
            }
            R.string.delete_account_confirm_title -> {
                handleDeleteAccount()
            }
            R.string.report_confirm_title -> {
                handleReport()
            }
            R.string.quit_confirm_title -> {
                handleQuit()
            }
        }
    }
    private fun handleHomeMatchingCancel() {
        handleYesButton { viewModel.postCancelMatching() }
        handleNoButton {
            saveNavigationResult(CONFIRM_MATCHING_CANCEL, CONFIRM_NO)
            popDirections()
        }
    }

    private fun handleProfileDismiss() {
        handleYesButton { } // TODO 프로필 교환 거절 api 추가
        handleNoButton { popDirections() }
    }

    private fun handleLogout() {
        handleYesButton { viewModel.logout() }
        handleNoButton { popDirections() }
    }

    private fun handleDeleteAccount() {
        handleYesButton {  viewModel.deleteAccount(args.reason) }
        handleNoButton { popDirections() }
    }

    private fun handleReport() {
        binding?.tvNo?.isGone = true

        handleYesButton {
            val title = getString(R.string.exit_complete_title).format(getStringData(NICKNAME))
            viewModel.exitChatRoomByReport(true, title)
        }
        handleNoButton {
            popDirections()
        }
    }

    private fun handleQuit() {
        val matchingId = args.matchingId
        val reason = args.reason

        handleYesButton {
            if (matchingId != 0 && reason != 0) {
                val title = getString(R.string.exit_complete_title).format(getStringData(NICKNAME))
                viewModel.exitChatRoom(matchingId, reason, false, title)
            } else {
                showToast(R.string.toast_fail)
            }
        }
        handleNoButton { popDirections() }
    }



    /** yes/no button **/
    private fun handleYesButton(onClickYesButton: () -> Unit) {
        binding?.tvYes?.setOnClickListener {
            onClickYesButton()
        }
    }

    private fun handleNoButton(onClickNoButton: () -> Unit) {
        binding?.tvNo?.setOnClickListener {
            onClickNoButton()
        }
    }

}
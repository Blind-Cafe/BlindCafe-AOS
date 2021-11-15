package com.abouttime.blindcafe.presentation.chat.quit.reason

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class QuitReasonDialogFragment:BaseDialogFragment<QuitReasonViewModel>(R.layout.dialog_fragment_quit_reason) {
    override val viewModel: QuitReasonViewModel by viewModel()
}
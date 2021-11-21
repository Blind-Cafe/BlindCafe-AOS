package com.abouttime.blindcafe.presentation.main.my_page.setting

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class SettingViewModel: BaseViewModel() {





    /** onClick **/
    fun onClickLogoutContainer(v: View) {

        moveToDirections(SettingFragmentDirections.actionSettingFragmentToConfirmDialogFragment(
            title = v.resources.getString(R.string.logout_confirm_title),
            subtitle = null,
            no = v.resources.getString(R.string.logout_confirm_no),
            yes = v.resources.getString(R.string.logout_confirm_yes)
        ))
    }

    fun onClickReportListContainer() {
        moveToDirections(SettingFragmentDirections.actionSettingFragmentToReportListFragment())
    }

    fun onClickAccountDeleteContainer() {
        moveToDirections(SettingFragmentDirections.actionSettingFragmentToAccountDeleteFragment())
    }

}
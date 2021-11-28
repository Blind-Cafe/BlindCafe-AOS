package com.abouttime.blindcafe.presentation.profile_exchange.accept

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.domain.model.Profile
import com.abouttime.blindcafe.domain.use_case.server.DeleteDismissMatchingUseCase
import com.abouttime.blindcafe.domain.use_case.server.GetPartnerProfileUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostAcceptMatchingUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExchangeAcceptViewModel(
    private val getPartnerProfileUseCase: GetPartnerProfileUseCase,
    private val postAcceptMatchingUseCase: PostAcceptMatchingUseCase,
    private val deleteDismissMatchingUseCase: DeleteDismissMatchingUseCase
): BaseViewModel() {

    private val _partnerProfile = MutableLiveData<Profile>()
    val partnerProfile: LiveData<Profile> get() =  _partnerProfile

    var matchingId: Int? = null
    var partnerUserId: Int? = null


    /** use cases **/
    fun getPartnerProfile(matchingId: Int) {
        getPartnerProfileUseCase(matchingId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.let {
                        _partnerProfile.value = it.toProfile()
                        partnerUserId = it.userId
                    }

                    dismissLoading()
                }
                is Resource.Error -> {
                    dismissLoading()
                }
            }

        }.launchIn(viewModelScope)

    }

    fun acceptMatching(matchingId: Int) {
        postAcceptMatchingUseCase(matchingId).onEach { result ->
            when (result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {

                }
                is Resource.Error -> {

                }
            }

        }.launchIn(viewModelScope)
    }

    fun dismissMatching(matchingId: Int, reason: Int) {
        deleteDismissMatchingUseCase(matchingId = matchingId, reason = reason).onEach { result ->
            when (result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {

                }
                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }



    /** onClick **/
    fun onClickDismissButton() {
        /** 거절이유 화면으로 이동 **/
    }
    fun onClickAcceptButton() {
        /** 30 번 api 호출 **/

    }
    fun onClickProfileImage() {
        partnerUserId?.let {
            moveToAcceptImageDialogFragment(it)
        }
    }

    /** navigate **/
    private fun moveToAcceptImageDialogFragment(userId: Int) {
        moveToDirections(ExchangeAcceptFragmentDirections.actionExchangeAcceptFragmentToAcceptImageDialogFragment(
            partnerUserId = userId
        ))
    }

    private fun moveToDismissFragment(matchingId: Int) {
        moveToDirections(ExchangeAcceptFragmentDirections.exchangeAcceptFragmentToExchangeDismissFragment())
    }
}
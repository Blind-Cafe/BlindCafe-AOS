package com.abouttime.blindcafe.presentation.onboarding.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.background.service.FirebaseService
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.constants.PreferenceKey.DEVICE_TOKEN
import com.abouttime.blindcafe.data.remote.server.dto.login.KakaoTokenDto
import com.abouttime.blindcafe.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    private var binding: FragmentLoginBinding? = null
    override val viewModel: LoginViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentLoginBinding = FragmentLoginBinding.bind(view)
        binding = fragmentLoginBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initKakaoLoginButton(fragmentLoginBinding)
    }

    private fun initKakaoLoginButton(fragmentLoginBinding: FragmentLoginBinding) {
        fragmentLoginBinding.btKakaoLogin.setOnClickListener {
            loginWithKakao()
        }

    }

    private fun loginWithKakao() = lifecycleScope.launch(Dispatchers.Main) {

        val deviceId = FirebaseService.token

        deviceId?.let { deviceId ->
            Log.e(DEVICE_TOKEN, deviceId)

            val callback2: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    val keyHash: String = Utility.getKeyHash(requireActivity() /* context */)
                    Log.e(LogTag.LOGIN_TAG, "로그인 실패 $keyHash", error)
                } else if (token != null) {
                    Log.i(LogTag.LOGIN_TAG, "로그인 성공 ${token.accessToken}")
                    viewModel.postKakaoToken(
                        KakaoTokenDto(
                            token.accessToken,
                            deviceId
                        )
                    )
                }
            }

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback2)
                } else if (token != null) {
                    Log.i(LogTag.LOGIN_TAG, "로그인 성공 ${token.accessToken}")
                    viewModel.postKakaoToken(
                        KakaoTokenDto(
                            token.accessToken,
                            deviceId
                        )
                    )
                }
            }
            viewModel.loginWithKakao(context = requireActivity(), callback = callback)


        }


    }


}
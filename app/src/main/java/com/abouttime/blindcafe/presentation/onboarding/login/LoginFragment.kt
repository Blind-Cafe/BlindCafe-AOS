package com.abouttime.blindcafe.presentation.onboarding.login

import android.os.Bundle
import android.util.Log
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.server.dto.KakaoToken
import com.abouttime.blindcafe.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    private var binding: FragmentLoginBinding? = null
    override val viewModel: LoginViewModel by viewModel()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentLoginBinding = FragmentLoginBinding.bind(view)
        binding = fragmentLoginBinding

        observeData(fragmentLoginBinding)
    }

    private fun observeData(fragmentLoginBinding: FragmentLoginBinding) {
        viewModel.loginStateEvent.observe(viewLifecycleOwner) { state ->
            when(state) {
                LoginState.Uninitialized -> {
                    initKakaoLoginButton(fragmentLoginBinding)
                }
                LoginState.Loading -> {

                }
                LoginState.Success -> {
                    moveToAgreementFragment()
                }
                LoginState.Error -> {
                    showToast(R.string.login_toast_error)
                }
            }

        }
    }

    private fun initKakaoLoginButton(fragmentLoginBinding: FragmentLoginBinding) {
        fragmentLoginBinding.btKakaoLogin.setOnClickListener {
            loginWithKakao()
        }

    }

    private fun loginWithKakao() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                val keyHash: String = Utility.getKeyHash(requireActivity() /* context */)
                Log.e(LogTag.LOGIN_TAG, "로그인 실패 ${keyHash}", error)
            } else if (token != null) {
                Log.i(LogTag.LOGIN_TAG, "로그인 성공 ${token.accessToken}")
                viewModel.postKakaoToken(KakaoToken(token.accessToken))
            }
        }
        viewModel.loginWithKakao(context = requireActivity(),callback = callback)
    }

    private fun moveToAgreementFragment() {
        moveToDirections(LoginFragmentDirections.actionLoginFragmentToAgreementFragment())
    }


}
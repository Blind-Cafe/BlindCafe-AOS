package com.abouttime.blindcafe.presentation.onboarding.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.remote.dto.KakaoToken
import com.abouttime.blindcafe.databinding.ActivityLoginBinding
import com.abouttime.blindcafe.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : Fragment(R.layout.fragment_login) {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by viewModel()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentLoginBinding = FragmentLoginBinding.bind(view)
        binding = fragmentLoginBinding

            Log.d("asdf", "asdf1")
        initKakaoLoginButton(fragmentLoginBinding)
    }

    private fun initKakaoLoginButton(fragmentLoginBinding: FragmentLoginBinding) {
        fragmentLoginBinding.btKakaoLogin.setOnClickListener {
            Log.d("asdf", "asdf2")
            loginWithKakao()
        }

    }

    private fun loginWithKakao() {
        Log.d("asdf", "asdf2");
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                val keyHash: String = Utility.getKeyHash(requireActivity() /* context */)
                Log.e(LogTag.LOGIN_TAG, "로그인 실패 ${keyHash}", error)
            } else if (token != null) {
                Log.i(LogTag.LOGIN_TAG, "로그인 성공 ${token.accessToken}")
                viewModel.postKakaoToken(KakaoToken(token.accessToken))

            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireActivity())) {
            UserApiClient.instance.loginWithKakaoTalk(requireActivity(), callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireActivity(), callback = callback)
        }


    }


}
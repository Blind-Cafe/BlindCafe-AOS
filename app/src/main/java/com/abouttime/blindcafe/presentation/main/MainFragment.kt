package com.abouttime.blindcafe.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentMainBinding
import com.abouttime.blindcafe.presentation.main.chat.ChatFragment
import com.abouttime.blindcafe.presentation.main.home.HomeFragment
import com.abouttime.blindcafe.presentation.main.my_page.MyPageFragment
import com.abouttime.blindcafe.presentation.main.my_page.MyPageViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : BaseFragment<MainViewModel>(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null
    override val viewModel: MainViewModel by viewModel()
    val homeFragment = HomeFragment()
    val chatFragment = ChatFragment()
    val myPageFragment = MyPageFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentMainBinding = FragmentMainBinding.bind(view)
        binding = fragmentMainBinding

        setCurrentFragment(homeFragment)
        initBottomNavigationView(fragmentMainBinding)
    }

    private fun initBottomNavigationView(fragmentMainBinding: FragmentMainBinding) {
        fragmentMainBinding.bnTab.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.menu_home -> setCurrentFragment(homeFragment)
                R.id.menu_matching -> {
                    viewModel.moveToMatchingFragment()
                    setCurrentFragment(homeFragment)
                }
                R.id.menu_chat -> setCurrentFragment(chatFragment)
                R.id.menu_my_page -> setCurrentFragment(myPageFragment)
            }
            true
        }
        fragmentMainBinding.bnTab.getOrCreateBadge(R.id.menu_matching).apply {
            number = 1
            isVisible = true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_container, fragment)
            commit()
        }
}
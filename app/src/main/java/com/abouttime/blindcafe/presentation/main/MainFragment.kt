package com.abouttime.blindcafe.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.LogTag.LIFECYCLE_TAG
import com.abouttime.blindcafe.databinding.FragmentMainBinding
import com.abouttime.blindcafe.presentation.main.chat_list.ChatListFragment
import com.abouttime.blindcafe.presentation.main.home.HomeFragment
import com.abouttime.blindcafe.presentation.main.my_page.MyPageFragment
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : BaseFragment<MainViewModel>(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null
    override val viewModel: MainViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentMainBinding = FragmentMainBinding.bind(view)
        binding = fragmentMainBinding



        //setCurrentFragment(HomeFragment())
        initBottomNavigationView(fragmentMainBinding)
    }

    private fun initBottomNavigationView(fragmentMainBinding: FragmentMainBinding) {

        fragmentMainBinding.bnTab.setOnItemSelectedListener { menuItem ->
            viewModel.lastPageId = menuItem.itemId
            setCurrentFragmentById(menuItem.itemId)
            true
        }
        //setCurrentFragmentById(viewModel.lastPageId)
        fragmentMainBinding.bnTab.selectedItemId = viewModel.lastPageId


    }
    private fun setCurrentFragmentById(id: Int) {
        when(id) {
            R.id.menu_home -> {
                binding?.bnTab?.setBackgroundResource(R.color.black_2)
                setCurrentFragment(HomeFragment())
            }
            R.id.menu_chat -> {
                binding?.bnTab?.setBackgroundResource(R.color.matching_room_root_bg)
                setCurrentFragment(ChatListFragment())
            }
            R.id.menu_my_page -> {
                binding?.bnTab?.setBackgroundResource(R.color.black_2)
                setCurrentFragment(MyPageFragment())
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_container, fragment)
            commit()
        }


    private fun createBadgeToBottomNavigationItem(menuId: Int, num: Int) {
        binding?.bnTab?.getOrCreateBadge(menuId)?.apply {
            number = num
            isVisible = true
        }
    }




}
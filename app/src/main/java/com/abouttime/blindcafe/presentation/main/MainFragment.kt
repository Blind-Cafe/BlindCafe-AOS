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



        setCurrentFragment(HomeFragment())
        initBottomNavigationView(fragmentMainBinding)
    }

    private fun initBottomNavigationView(fragmentMainBinding: FragmentMainBinding) {

        fragmentMainBinding.bnTab.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.menu_home -> setCurrentFragment(HomeFragment())
                R.id.menu_chat -> setCurrentFragment(ChatListFragment())
                R.id.menu_my_page -> setCurrentFragment(MyPageFragment())
            }
            true
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(LIFECYCLE_TAG, "onCreate")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LIFECYCLE_TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(LIFECYCLE_TAG, "onViewStateRestored")

    }

    override fun onStart() {
        super.onStart()
        Log.d(LIFECYCLE_TAG, "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d(LIFECYCLE_TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LIFECYCLE_TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LIFECYCLE_TAG, "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LIFECYCLE_TAG, "onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(LIFECYCLE_TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LIFECYCLE_TAG, "onDestroy")
    }





}
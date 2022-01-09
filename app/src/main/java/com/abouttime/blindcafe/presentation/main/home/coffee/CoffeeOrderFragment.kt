package com.abouttime.blindcafe.presentation.main.home.coffee

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentCoffeeOrderBinding
import org.koin.android.viewmodel.ext.android.viewModel


class CoffeeOrderFragment : BaseFragment<CoffeeOrderViewModel>(R.layout.fragment_coffee_order) {
    override val viewModel: CoffeeOrderViewModel by viewModel()
    private var binding: FragmentCoffeeOrderBinding? = null
    private lateinit var vpAdapter: CoffeeOrderVpAdapter

    val args: CoffeeOrderFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentCoffeeOrderBinding = FragmentCoffeeOrderBinding.bind(view)
        binding = fragmentCoffeeOrderBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initArgsData()

    }

    private fun initArgsData() {
        viewModel.matchingId = args.matchingId
        viewModel.startTime = args.startTime
        viewModel.partnerNickname = args.partnerNickname
        initPartnerNickName()
    }

    private fun initPartnerNickName() {
        binding?.tvDescription?.text = getString(R.string.coffee_order_description).format(viewModel.partnerNickname)
    }

    override fun onResume() {
        super.onResume()
        initVpAdapter()
    }

    private fun initVpAdapter() {
        binding?.let {
            vpAdapter = CoffeeOrderVpAdapter(viewModel)
            it.vpImageContainer.adapter = vpAdapter
            it.vpImageContainer.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            it.vpImageContainer.offscreenPageLimit = 1
            it.vpImageContainer.setPadding(40, 0, 40, 0)

            it.vpImageContainer.setPageTransformer(ZoomOutPageTransformer())
        }


    }

}
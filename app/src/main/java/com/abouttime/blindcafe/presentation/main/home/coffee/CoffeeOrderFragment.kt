package com.abouttime.blindcafe.presentation.main.home.coffee

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentCoffeeOrderBinding
import com.abouttime.blindcafe.presentation.common.confirm.ConfirmDialogFragmentArgs
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
    }

    override fun onResume() {
        super.onResume()
        initVpAdapter()
    }

    private fun initVpAdapter() {
        Log.d("adsf", "$binding")
        binding?.let {
            vpAdapter = CoffeeOrderVpAdapter(viewModel)
            it.vpImageContainer.adapter = vpAdapter
            it.vpImageContainer.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            it.vpImageContainer.offscreenPageLimit = 1
            it.vpImageContainer.setPadding(40, 0, 40, 0)

            it.vpImageContainer.setPageTransformer(ZoomOutPageTransformer())
        }
    }

    fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp, context.getResources().getDisplayMetrics())
    }

}
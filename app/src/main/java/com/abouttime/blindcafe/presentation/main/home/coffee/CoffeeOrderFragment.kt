package com.abouttime.blindcafe.presentation.main.home.coffee

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentCoffeeOrderBinding
import org.koin.android.viewmodel.ext.android.viewModel
import android.util.TypedValue




class CoffeeOrderFragment: BaseFragment<CoffeeOrderViewModel>(R.layout.fragment_coffee_order) {
    override val viewModel: CoffeeOrderViewModel by viewModel()
    private var binding: FragmentCoffeeOrderBinding? = null
    private lateinit var vpAdapter: CoffeeOrderVpAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentCoffeeOrderBinding = FragmentCoffeeOrderBinding.bind(view)
        binding = fragmentCoffeeOrderBinding
        initVpAdapter(fragmentCoffeeOrderBinding)

    }

    private fun initVpAdapter(fragmentCoffeeOrderBinding: FragmentCoffeeOrderBinding) = with(fragmentCoffeeOrderBinding) {
        viewModel?.let {
            vpAdapter = CoffeeOrderVpAdapter(viewModel!!)
            vpImageContainer.adapter = vpAdapter
            vpImageContainer.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            vpImageContainer.offscreenPageLimit = 1
            vpImageContainer.setPadding(40, 0, 40, 0)

            vpImageContainer.setPageTransformer(ZoomOutPageTransformer())
        }


    }

    fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp, context.getResources().getDisplayMetrics())
    }

}
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
        vpAdapter = CoffeeOrderVpAdapter(viewModel)
        vpImageContainer.adapter = vpAdapter
        vpImageContainer.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vpImageContainer.offscreenPageLimit = 1
        vpImageContainer.setPadding(40, 0, 40, 0)
        /* 여백, 너비에 대한 정의 */
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.coffee_page_margin) // dimen 파일 안에 크기를 정의해두었다.
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.coffee_page_width) // dimen 파일이 없으면 생성해야함
        val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 너비 길이를 가져옴
        val offsetPx = screenWidth - pageMarginPx - pagerWidth
        vpImageContainer.setPageTransformer(ZoomOutPageTransformer())

        vpImageContainer.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }
    }

    fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp, context.getResources().getDisplayMetrics())
    }

}
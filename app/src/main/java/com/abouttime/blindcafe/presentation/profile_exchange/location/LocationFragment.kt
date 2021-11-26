package com.abouttime.blindcafe.presentation.profile_exchange.location

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentLocationBinding
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.koin.android.viewmodel.ext.android.viewModel

class LocationFragment: BaseFragment<LocationViewModel>(R.layout.fragment_location){
    override val viewModel: LocationViewModel by viewModel()
    private var binding: FragmentLocationBinding? = null
    private lateinit var locationMainAdapter: LocationMainAdapter
    private lateinit var locationSubAdapter: LocationSubAdapter



    private lateinit var mainLocations: List<String>

    private lateinit var subLocations: List<List<String>>



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentLocationBinding = FragmentLocationBinding.bind(view)
        binding = fragmentLocationBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        mainLocations = resources.getStringArray(R.array.location_main).toList()
        subLocations = listOf(
            resources.getStringArray(R.array.location_sub_0).toList(),
            resources.getStringArray(R.array.location_sub_1).toList(),
            resources.getStringArray(R.array.location_sub_2).toList(),
            resources.getStringArray(R.array.location_sub_3).toList(),
            resources.getStringArray(R.array.location_sub_4).toList(),
            resources.getStringArray(R.array.location_sub_5).toList(),
            resources.getStringArray(R.array.location_sub_6).toList(),
            resources.getStringArray(R.array.location_sub_7).toList(),
            resources.getStringArray(R.array.location_sub_8).toList(),
            resources.getStringArray(R.array.location_sub_9).toList(),
            resources.getStringArray(R.array.location_sub_10).toList(),
            resources.getStringArray(R.array.location_sub_11).toList(),
            resources.getStringArray(R.array.location_sub_12).toList(),
            resources.getStringArray(R.array.location_sub_13).toList(),
            resources.getStringArray(R.array.location_sub_14).toList(),
            resources.getStringArray(R.array.location_sub_15).toList(),
            resources.getStringArray(R.array.location_sub_16).toList(),
        )


        initMainRecyclerView(fragmentLocationBinding)
        initSubRecyclerView(fragmentLocationBinding)

    }

    private fun initMainRecyclerView(fragmentLocationBinding: FragmentLocationBinding) = with(fragmentLocationBinding) {
        viewModel?.let { vm ->
            locationMainAdapter = LocationMainAdapter(vm) { position ->
                setSubLocations(position)
            }
            rvLocationMain.apply {
                adapter = locationMainAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            OverScrollDecoratorHelper.setUpOverScroll(rvLocationMain, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)


            vm.setMainLocation(mainLocations)
            locationMainAdapter.notifyDataSetChanged()
        }


    }
    private fun initSubRecyclerView(fragmentLocationBinding: FragmentLocationBinding) = with(fragmentLocationBinding) {
        viewModel?.let { vm ->
            locationSubAdapter = LocationSubAdapter(vm)
            rvLocationSub.apply {
                adapter = locationSubAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            vm.setSubLocation(subLocations[0])
            locationSubAdapter.notifyDataSetChanged()
            OverScrollDecoratorHelper.setUpOverScroll(rvLocationSub, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
        }
    }

    private fun setSubLocations(position: Int) {
        viewModel?.setSubLocation(subLocations[position])
        viewModel?.selectedSubLocation = -1
        locationSubAdapter.notifyDataSetChanged()
    }

}
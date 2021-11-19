package com.abouttime.blindcafe.presentation.main.my_page.edit.location

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentLocationBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LocationFragment: BaseFragment<LocationViewModel>(R.layout.fragment_location){
    override val viewModel: LocationViewModel by viewModel()
    private var binding: FragmentLocationBinding? = null
    private lateinit var locationMainAdapter: LocationMainAdapter
    private lateinit var locationSubAdapter: LocationSubAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentLocationBinding = FragmentLocationBinding.bind(view)
        binding = fragmentLocationBinding

        initMainRecyclerView(fragmentLocationBinding)
        initSubRecyclerView(fragmentLocationBinding)

    }

    private fun initMainRecyclerView(fragmentLocationBinding: FragmentLocationBinding) = with(fragmentLocationBinding) {
        locationMainAdapter = LocationMainAdapter(viewModel)
        rvLocationMain.apply {
            adapter = locationMainAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val mainLocations = resources.getStringArray(R.array.location_main).toList()
        viewModel.setMainLocation(mainLocations)

    }
    private fun initSubRecyclerView(fragmentLocationBinding: FragmentLocationBinding) = with(fragmentLocationBinding) {
        locationSubAdapter = LocationSubAdapter(viewModel)
        rvLocationSub.apply {
            adapter = locationSubAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val subLocations = resources.getStringArray(R.array.location_sub_0).toList()
        viewModel.setSubLocation(subLocations)
    }

}
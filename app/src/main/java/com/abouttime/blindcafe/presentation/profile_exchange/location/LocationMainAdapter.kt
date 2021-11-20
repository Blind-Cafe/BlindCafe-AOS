package com.abouttime.blindcafe.presentation.profile_exchange.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.databinding.RvItemLocationMainBinding

class LocationMainAdapter(
    private val viewModel: LocationViewModel
): RecyclerView.Adapter<LocationMainAdapter.ViewHolder>() {



    inner class ViewHolder(private val binding: RvItemLocationMainBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindResource(data: String) = with(binding) {
            tvLocationMain.text = data
        }

        fun bindView(data: String) = with(binding) {
            root.setOnClickListener {

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RvItemLocationMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindResource(viewModel.mainLocations[position])
        holder.bindView(viewModel.mainLocations[position])
    }

    override fun getItemCount(): Int = viewModel.mainLocations.size


}
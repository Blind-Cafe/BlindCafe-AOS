package com.abouttime.blindcafe.presentation.edit.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvItemLocationSubBinding


class LocationSubAdapter(
    private val viewModel: LocationViewModel
): RecyclerView.Adapter<LocationSubAdapter.ViewHolder>() {



    inner class ViewHolder(private val binding: RvItemLocationSubBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindResource(position: Int) = with(binding) {
            if (viewModel.selectedSubLocation == position) {
                tvLocationSub.setBackgroundResource(R.color.location_sub_background)
            } else {
                tvLocationSub.setBackgroundResource(R.color.gray_900)
            }
            tvLocationSub.text = viewModel.subLocations[position]
        }

        fun bindView(position: Int) = with(binding) {
            root.setOnClickListener {
                viewModel.selectedSubLocation = position
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RvItemLocationSubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindResource(position)
        holder.bindView(position)
    }

    override fun getItemCount(): Int = viewModel.subLocations.size


}
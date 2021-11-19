package com.abouttime.blindcafe.presentation.main.my_page.edit.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.databinding.RvItemLocationSubBinding


class LocationSubAdapter(
    private val viewModel: LocationViewModel
): RecyclerView.Adapter<LocationSubAdapter.ViewHolder>() {



    inner class ViewHolder(private val binding: RvItemLocationSubBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindResource(data: String) = with(binding) {
            tvLocationSub.text = data
        }

        fun bindView(data: String) = with(binding) {
            root.setOnClickListener {

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RvItemLocationSubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindResource(viewModel.subLocations[position])
        holder.bindView(viewModel.subLocations[position])
    }

    override fun getItemCount(): Int = viewModel.subLocations.size


}
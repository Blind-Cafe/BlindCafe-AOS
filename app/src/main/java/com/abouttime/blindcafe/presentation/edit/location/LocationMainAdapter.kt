package com.abouttime.blindcafe.presentation.edit.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvItemLocationMainBinding

class LocationMainAdapter(
    private val viewModel: LocationViewModel,
    private val onClickItem: (Int) -> Unit
): RecyclerView.Adapter<LocationMainAdapter.ViewHolder>() {



    inner class ViewHolder(private val binding: RvItemLocationMainBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindResource(position: Int) = with(binding) {
            if (position == viewModel.selectedMainLocation) {
                tvLocationMain.setBackgroundResource(R.drawable.bg_location_main)
            } else {
                tvLocationMain.setBackgroundResource(R.color.gray_900)
            }
            tvLocationMain.text = viewModel.mainLocations[position]
        }

        fun bindView(position: Int) = with(binding) {
            root.setOnClickListener {
                viewModel.selectedMainLocation = position
                onClickItem(position)
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RvItemLocationMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindResource(position)
        holder.bindView(position)
    }

    override fun getItemCount(): Int = viewModel.mainLocations.size



}
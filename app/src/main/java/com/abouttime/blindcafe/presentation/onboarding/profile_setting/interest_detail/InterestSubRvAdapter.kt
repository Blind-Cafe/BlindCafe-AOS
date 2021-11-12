package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.databinding.RvItemInterestSubBinding

class InterestSubRvAdapter(
   private val viewModel: InterestSubViewModel
): RecyclerView.Adapter<InterestSubRvAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: RvItemInterestSubBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindResource(position: Int) {

        }

        fun bindView(position: Int) {

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RvItemInterestSubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindResource(position)
        holder.bindView(position)
    }

    override fun getItemCount(): Int = 3


}
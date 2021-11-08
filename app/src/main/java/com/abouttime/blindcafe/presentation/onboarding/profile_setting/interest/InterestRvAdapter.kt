package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvItemInterestBinding

class InterestRvAdapter(
    private val context: Context,
    private val viewModel: InterestViewModel,
    private val isClickedThreeItem: (Boolean) -> Unit
): RecyclerView.Adapter<InterestRvAdapter.ViewHolder>() {

    private var interests = mutableListOf(
        R.drawable.bt_interest_1,
        R.drawable.bt_interest_2,
        R.drawable.bt_interest_3,
        R.drawable.bt_interest_4,
        R.drawable.bt_interest_5,
        R.drawable.bt_interest_6,
        R.drawable.bt_interest_7,
        R.drawable.bt_interest_8,
        R.drawable.bt_interest_9
    )

    private val isSelected = Array(9) { false }


    inner class ViewHolder(private val binding: RvItemInterestBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindResource(position: Int) {
            binding.ivItem.setImageResource(interests[position])

            if (isSelected[position].not()) {
                binding.ivItem.setColorFilter(context.resources.getColor(R.color.button_disabled, null))
            } else {
                binding.ivItem.setColorFilter(context.resources.getColor(R.color.button_enabled, null))
            }
        }

        fun bindView(position: Int) {
            binding.ivItem.setOnClickListener {
                with(viewModel) {
                    if (isSelected[position]) {
                        if (getSelectedItemCount() < 0) return@setOnClickListener
                        selectedItemIdx.remove(position)

                    } else {
                        if (getSelectedItemCount() >= 3) return@setOnClickListener
                        selectedItemIdx.add(position)

                    }

                    isClickedThreeItem(getSelectedItemCount() == 3)

                    isSelected[position] = isSelected[position].not()
                    notifyItemChanged(position)
                }

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RvItemInterestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindResource(position)
        holder.bindView(position)
    }

    override fun getItemCount(): Int = interests.size


}
package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvItemInterestBinding

class InterestRvAdapter(
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

    private val selectedInterests = mutableListOf(
        R.drawable.bt_interest_selected_1,
        R.drawable.bt_interest_selected_2,
        R.drawable.bt_interest_selected_3,
        R.drawable.bt_interest_selected_4,
        R.drawable.bt_interest_selected_5,
        R.drawable.bt_interest_selected_6,
        R.drawable.bt_interest_selected_7,
        R.drawable.bt_interest_selected_8,
        R.drawable.bt_interest_selected_9,
    )

    private val isSelected = Array(9) { false }

    var SelectedItemCount = 0


    inner class ViewHolder(private val binding: RvItemInterestBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindResource(position: Int) {
            if (isSelected[position].not()) {
                binding.ivItem.setImageResource(interests[position])
            } else {
                binding.ivItem.setImageResource(selectedInterests[position])
            }
        }
        fun bindView(position: Int) {
            binding.ivItem.setOnClickListener {

                if (isSelected[position]) {
                    if (SelectedItemCount < 0) return@setOnClickListener
                    SelectedItemCount -= 1
                } else {
                    if (SelectedItemCount >= 3) return@setOnClickListener

                    SelectedItemCount += 1
                }

                isClickedThreeItem(SelectedItemCount == 3)

                isSelected[position] = isSelected[position].not()
                notifyItemChanged(position)


            }
        }
    }

    fun getSelectedItems(): List<Int> {
        val result = mutableListOf<Int>()
        isSelected.forEachIndexed { index, b ->
            if (b) result.add(index)
        }
        return result
    }

    fun isSelectedThreeItems(): Boolean = SelectedItemCount == 3

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
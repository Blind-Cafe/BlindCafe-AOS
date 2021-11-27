package com.abouttime.blindcafe.presentation.edit.interest.sub

import android.view.View
import androidx.core.content.ContextCompat
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.data.server.dto.interest.Interest
import com.abouttime.blindcafe.databinding.RvItemInterestSubBinding
import com.xwray.groupie.viewbinding.BindableItem



class InterestSubEditItem(
    private val interest: Interest,
    private val interestViewModel: InterestSubEditViewModel,
    private val index: Int,
) : BindableItem<RvItemInterestSubBinding>() {

    override fun bind(binding: RvItemInterestSubBinding, position: Int) {
        with(binding) {
            root.setBackgroundColor(root.resources.getColor(R.color.black, null))
            val subInterests = arrayOf(
                tvSubInterest1,
                tvSubInterest2,
                tvSubInterest3,
                tvSubInterest4,
                tvSubInterest5,
                tvSubInterest6,
                tvSubInterest7,
                tvSubInterest8,
                tvSubInterest9
            )

            tvTitle.text = interestViewModel.interestMap[interest.main]
            subInterests.forEachIndexed { i, v ->
                v.text = interest.sub[i]
            }

            interestViewModel?.let { vm ->
                with(vm) {
                    val selected = selectedSubInterests[index]
                    for (i in subInterests.indices) {
                        subInterests[i].setOnClickListener { v ->
                            if (selected.contains(interest.sub[i])) {
                                subInterests[i].setTextColor(v.resources.getColor(R.color.gray_300,
                                    null))
                                subInterests[i].background.setTint(ContextCompat.getColor(v.context, R.color.sub_interest_disabled))
                                selected.remove(interest.sub[i])
                            } else {
                                subInterests[i].setTextColor(v.resources.getColor(R.color.white,
                                    null))
                                subInterests[i].background.setTint(ContextCompat.getColor(v.context, R.color.main))
                                selected.add(interest.sub[i])
                            }
                            updateNextButton()
                        }
                    }
                }
            }
        }
    }



    override fun getLayout(): Int = R.layout.rv_item_interest_sub

    override fun initializeViewBinding(view: View): RvItemInterestSubBinding =
        RvItemInterestSubBinding.bind(view)

}
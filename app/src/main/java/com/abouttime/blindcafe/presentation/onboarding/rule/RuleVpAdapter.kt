package com.abouttime.blindcafe.presentation.onboarding.rule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.databinding.VpItemRuleBinding

class RuleVpAdapter(
    private val resIds: List<Int>,

): RecyclerView.Adapter<RuleVpAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: VpItemRuleBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(resId: Int) {
            binding.ivItem.setImageResource(resId)
        }

    }
    // 어떤 뷰를 만들 것인가
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
           VpItemRuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    // 어떤 뷰를 바인드할 것인가
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(resIds[position])
    }

    // 몇개의 뷰를 보여줄 것인가
    override fun getItemCount(): Int = resIds.size


}
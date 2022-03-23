package com.abouttime.blindcafe.presentation.login.rule

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
           VpItemRuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(resIds[position])
    }


    override fun getItemCount(): Int = resIds.size


}
package com.abouttime.blindcafe.presentation.main.home.coffee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.VpItemCoffeeBinding


class CoffeeOrderVpAdapter(
    private val viewModel: CoffeeOrderViewModel,
) : RecyclerView.Adapter<CoffeeOrderVpAdapter.ViewHolder>() {




    inner class ViewHolder(private val binding: VpItemCoffeeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) = with(viewModel)  {
            if (isSelected[position]) {
                binding.ivItem.setImageResource(selectedResId[position])
            } else {
                binding.ivItem.setImageResource(resIds[position])
            }
        }

        fun bindView(position: Int) = with(viewModel) {
            binding.ivItem.setOnClickListener {
                if (!isSelected[position]) {
                    for (i in isSelected.indices) {
                        isSelected[i] = false
                    }
                }
                isSelected[position] = !isSelected[position]
                if (isSelected[position]) {
                    currentSelect = position + 1
                } else {
                    currentSelect = -1
                }
                updateNextButton()
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            VpItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
        holder.bindView(position)
    }

    override fun getItemCount(): Int = viewModel.resIds.size


}
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
                updateNextButton()
                notifyDataSetChanged()
            }
        }
    }

    // 어떤 뷰를 만들 것인가
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            VpItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    // 어떤 뷰를 바인드할 것인가
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
        holder.bindView(position)
    }

    // 몇개의 뷰를 보여줄 것인가
    override fun getItemCount(): Int = viewModel.resIds.size


}
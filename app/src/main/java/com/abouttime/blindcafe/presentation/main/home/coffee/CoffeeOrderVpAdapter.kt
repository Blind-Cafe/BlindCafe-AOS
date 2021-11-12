package com.abouttime.blindcafe.presentation.main.home.coffee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.VpItemCoffeeBinding


class CoffeeOrderVpAdapter(
    private val viewModel: CoffeeOrderViewModel,
) : RecyclerView.Adapter<CoffeeOrderVpAdapter.ViewHolder>() {


    private val resIds: List<Int> = mutableListOf(
        R.drawable.bt_coffee_americano,
        R.drawable.bt_coffee_cafe_latte,
        R.drawable.bt_coffee_cafe_mocha,
        R.drawable.bt_coffee_bubble_tea,
        R.drawable.bt_coffee_mint_chocolate,
        R.drawable.bt_coffee_strawberry_smoothie,
        R.drawable.bt_coffee_blue_lemonade,
        R.drawable.bt_coffee_green_tea,
        R.drawable.bt_coffee_grapefruit_tea,
    )

    private val selectedResId: List<Int> = mutableListOf(
        R.drawable.bt_coffee_americano_selected,
        R.drawable.bt_coffee_cafe_latte_selected,
        R.drawable.bt_coffee_cafe_mocha_selected,
        R.drawable.bt_coffee_bubble_tea_selected,
        R.drawable.bt_coffee_mint_chocolate_selected,
        R.drawable.bt_coffee_strawberry_smoothie_selected,
        R.drawable.bt_coffee_blue_lemonade_selected,
        R.drawable.bt_coffee_green_tea_selected,
        R.drawable.bt_coffee_grapefruit_tea_selected,
    )
    private val isSelected = Array(9) { false }

    inner class ViewHolder(private val binding: VpItemCoffeeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
            if (isSelected[position]) {
                binding.ivItem.setImageResource(selectedResId[position])
            } else {
                binding.ivItem.setImageResource(resIds[position])
            }
        }

        fun bindView(position: Int) {
            binding.ivItem.setOnClickListener {
                if (!isSelected[position]) {
                    for (i in isSelected.indices) {
                        isSelected[i] = false
                    }
                }
                isSelected[position] = !isSelected[position]
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
    override fun getItemCount(): Int = resIds.size


}
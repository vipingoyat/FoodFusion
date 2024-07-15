package com.example.foodfusion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.databinding.BuyAgainItemBinding
import java.util.zip.Inflater

class BuyAgainAdapter(private val buyAgainFoodName:ArrayList<String>, private val buyAgainFoodPrice:ArrayList<String>,
    private val buyAgainFoodImage:ArrayList<Int>):RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
            return BuyAgainViewHolder(BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(buyAgainFoodName[position],buyAgainFoodPrice[position],buyAgainFoodImage[position])
    }
    override fun getItemCount(): Int = buyAgainFoodName.size

    class BuyAgainViewHolder(private val binding: BuyAgainItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, foodImage: Int) {
            binding.buyagainFoodName.text = foodName
            binding.butagainPrice.text = foodPrice
            binding.imageView10.setImageResource(foodImage)
        }


    }
}
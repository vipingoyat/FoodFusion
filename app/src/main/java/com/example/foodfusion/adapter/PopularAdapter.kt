package com.example.foodfusion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.databinding.PopularItemListBinding

class PopularAdapter(private val items:List<String> ,private val price:List<String>, private val image:List<Int>): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = items[position]
        val images = image[position]
        val price = price[position]
        holder.bind(item, images, price)
    }
    override fun getItemCount(): Int {
        return items.size
    }

    class PopularViewHolder(private val binding: PopularItemListBinding): RecyclerView.ViewHolder(binding.root){
        private val imageView = binding.imageView9
        fun bind(item: String, images: Int, price: String) {
            binding.foodnamepopular.text = item
            binding.pricepopular.text = price
            imageView.setImageResource(images)

        }

    }
}
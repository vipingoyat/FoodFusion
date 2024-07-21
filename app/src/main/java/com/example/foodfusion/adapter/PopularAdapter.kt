package com.example.foodfusion.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.DetailsActivity
import com.example.foodfusion.databinding.PopularItemListBinding

class PopularAdapter(private val items:List<String> ,private val price:List<String>, private val image:List<Int>, private val requireContext:Context): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = items[position]
        val images = image[position]
        val price = price[position]
        holder.bind(item, images, price)

        holder.itemView.setOnClickListener{
            //SetonClickListner to Open Details
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName", item)
            intent.putExtra("MenuItemImage", images)
            requireContext.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }

    class PopularViewHolder(private val binding: PopularItemListBinding): RecyclerView.ViewHolder(binding.root){
        private val imageView = binding.popularimage
        fun bind(item: String, images: Int, price: String) {
            binding.popularfoodname.text = item
            binding.popularprice.text = price
            imageView.setImageResource(images)

        }

    }
}
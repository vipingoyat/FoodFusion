package com.example.foodfusion.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodfusion.DetailsActivity
import com.example.foodfusion.databinding.MenuItemBinding

class MenuAdapter(
    private val menuItems: List<com.example.foodfusion.model.MenuItem>,
    private val requireContext: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            MenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem = menuItems[position]

            //a intent to open detail activity and pass data
            val intent = Intent(requireContext,DetailsActivity::class.java).apply {
                putExtra("MenuItemName",menuItem.foodName)
                putExtra("MenuItemPrice",menuItem.foodPrice)
                putExtra("MenuItemDescription",menuItem.foodDescription)
                putExtra("MenuItemIngredients",menuItem.foodIngredients)
                putExtra("MenuItemImage",menuItem.foodImage)
            }


            //Start the Detail Activity
            requireContext.startActivity(intent)
        }


        //Set Data into Recycler View items food name, price and image
        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menufoodname.text = menuItem.foodName
                menuprice.text = menuItem.foodPrice
                val Uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(Uri).into(menuimage)
            }
        }


    }
}



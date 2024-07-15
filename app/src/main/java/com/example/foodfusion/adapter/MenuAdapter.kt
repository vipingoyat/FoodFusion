package com.example.foodfusion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.databinding.MenuItemBinding

class MenuAdapter(private val Menuitemname:List<String>,private val Menuitemprice:List<String>,private val Menuimage:List<Int>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = Menuitemname.size

    inner class MenuViewHolder(private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                menufoodname.text = Menuitemname[position]
                menuprice.text = Menuitemprice[position]
                menuimage.setImageResource(Menuimage[position])
            }
        }

    }
}
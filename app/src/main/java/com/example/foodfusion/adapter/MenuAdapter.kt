package com.example.foodfusion.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.DetailsActivity
import com.example.foodfusion.databinding.MenuItemBinding

class MenuAdapter(private val Menuitemname:List<String>,private val Menuitemprice:List<String>,private val Menuimage:List<Int>, private val requireContext:Context) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){

private val itemClickListener: OnClickListener ?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = Menuitemname.size

    inner class MenuViewHolder(private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    itemClickListener?.onItemClick(position)
                }
                //SetonClickListner to Open Details
                val intent = Intent(requireContext, DetailsActivity::class.java)
                intent.putExtra("MenuItemName", Menuitemname.get(position))
                intent.putExtra("MenuItemImage", Menuimage.get(position))
                requireContext.startActivity(intent)
            }
        }
        fun bind(position: Int) {
            binding.apply {
                menufoodname.text = Menuitemname[position]
                menuprice.text = Menuitemprice[position]
                menuimage.setImageResource(Menuimage[position])
            }
        }

    }
    interface OnClickListener{
        fun onItemClick(position: Int) {

        }
    }
}



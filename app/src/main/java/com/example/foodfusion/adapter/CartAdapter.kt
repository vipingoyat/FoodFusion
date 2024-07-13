package com.example.foodfusion.adapter

import android.os.Binder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.databinding.CartItemBinding

class CartAdapter(private val cartItems:MutableList<String>, private val cartItemPrice:MutableList<String>, private var CartImage:MutableList<Int>): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

private val itemQuantities = IntArray(cartItems.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }



    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartViewHolder(private val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(position: Int){
                binding.apply {
                    val quantity = itemQuantities[position]
                    cartFoodName.text = cartItems[position]
                    cartItemprice.text = cartItemPrice[position]
                    cartimage.setImageResource(CartImage[position])
                    cartItemQuantity.text = quantity.toString()
                }
            }
    }
}
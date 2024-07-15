package com.example.foodfusion.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfusion.R
import com.example.foodfusion.adapter.CartAdapter
import com.example.foodfusion.databinding.CartItemBinding
import com.example.foodfusion.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false)


        val cartFoodName = listOf("Burger", "Sandwich","Rasmalai","Pizza")
        val cartItemPrice = listOf("Rs 89","Rs 69", "Rs 59","Rs 129")
        val cartImage = listOf(
            R.drawable.burger_pic,
            R.drawable.sandwich_pic,
            R.drawable.rasmalai_pic,
            R.drawable.pizza_pic
        )
        val adapter = CartAdapter(ArrayList(cartFoodName),ArrayList(cartItemPrice), ArrayList(cartImage))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {
    }
}
package com.example.foodfusion.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfusion.R
import com.example.foodfusion.adapter.BuyAgainAdapter
import com.example.foodfusion.databinding.FragmentHistoryBinding
import com.example.foodfusion.databinding.FragmentHomeBinding

class HistoryFragment : Fragment() {
private lateinit var binding: FragmentHistoryBinding
private lateinit var buyAgainAdapter: BuyAgainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater,container,false)
        setupRecyclerView()


        return binding.root
    }
    private fun setupRecyclerView(){
        val buyagainfoodName = arrayListOf("Burger", "Sandwich","Rasmalai","Pizza","Burger", "Sandwich","Rasmalai","Pizza")
        val buyagainfoodprice = arrayListOf("Rs 89","Rs 69", "Rs 59","Rs 129","Rs 89","Rs 69", "Rs 59","Rs 129")
        val buyagainfoodImage = arrayListOf(R.drawable.burger_pic,
            R.drawable.sandwich_pic,
            R.drawable.rasmalai_pic,
            R.drawable.pizza_pic,
            R.drawable.burger_pic,
            R.drawable.sandwich_pic,
            R.drawable.rasmalai_pic,
            R.drawable.pizza_pic)

        buyAgainAdapter = BuyAgainAdapter(buyagainfoodName,buyagainfoodprice,buyagainfoodImage)
        binding.buyAgainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.buyAgainRecyclerView.adapter = buyAgainAdapter
    }

    companion object {
    }
}
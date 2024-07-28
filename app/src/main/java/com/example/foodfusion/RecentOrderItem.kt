package com.example.foodfusion

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfusion.adapter.RecentOrderAdapter
import com.example.foodfusion.databinding.ActivityRecentOrderItemBinding
import com.example.foodfusion.model.OrderDetails

class RecentOrderItem : AppCompatActivity() {
    private val binding : ActivityRecentOrderItemBinding by lazy{
        ActivityRecentOrderItemBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames:ArrayList<String>
    private lateinit var allFoodPrices:ArrayList<String>
    private lateinit var allFoodImages:ArrayList<String>
    private lateinit var allFoodQuantities:ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.recenOrderBackButton.setOnClickListener {
            finish()
        }

        val recentOrderItems = intent.getSerializableExtra("RecentBuyOrderItems") as ArrayList<OrderDetails>
        recentOrderItems?.let { orderDetails ->
            if(orderDetails.isNotEmpty()){
                val recentOrderItem= orderDetails[0]
                allFoodNames = recentOrderItem.foodNames as ArrayList<String>
                allFoodPrices = recentOrderItem.foodPrices as ArrayList<String>
                allFoodImages = recentOrderItem.foodImages as ArrayList<String>
                allFoodQuantities = recentOrderItem.foodQuantities as ArrayList<Int>
            }

        }
        setAdapter()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setAdapter() {
        val rv = binding.recentRecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentOrderAdapter(this,allFoodNames,allFoodImages,allFoodPrices,allFoodQuantities)
        rv.adapter = adapter
    }
}
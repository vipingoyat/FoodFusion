package com.example.foodfusion

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.foodfusion.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var foodName:String ?= null
    private var foodPrice:String ?= null
    private var foodImage:String ?= null
    private var foodDescription:String ?= null
    private var foodIngredients:String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodName = intent.getStringExtra("foodName")
        foodPrice = intent.getStringExtra("foodPrice")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodIngredients = intent.getStringExtra("MenuItemIngredients")
        foodImage = intent.getStringExtra("foodImage")

        with(binding){
            DetailFoodName.text = foodName
            descriptionTextView.text = foodDescription
            ingredientsTextView.text = foodIngredients
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(DetailFoodImage)
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.imageView11.setOnClickListener {
            finish()
        }
    }
}
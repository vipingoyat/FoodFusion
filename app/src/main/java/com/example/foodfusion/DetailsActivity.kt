package com.example.foodfusion

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.foodfusion.databinding.ActivityDetailsBinding
import com.example.foodfusion.model.CartItem
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var foodName:String ?= null
    private var foodPrice:String ?= null
    private var foodImage:String ?= null
    private var foodDescription:String ?= null
    private var foodIngredients:String ?= null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize firebase auth
        auth = FirebaseAuth.getInstance()

        foodName = intent.getStringExtra("MenuItemName")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodIngredients = intent.getStringExtra("MenuItemIngredients")
        foodImage = intent.getStringExtra("MenuItemImage")

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

        binding.button3.setOnClickListener {
            addItemtoCart()
        }
    }

    private fun addItemtoCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""

        //Create a cart item object
        val cartItem = CartItem(foodName.toString(),foodPrice.toString(),foodDescription.toString(),foodImage.toString(), 1,foodIngredients)

        //Save data to cart items to firebase database
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this,"Item is Added to Cart",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Item Not Added",Toast.LENGTH_SHORT).show()
        }

    }
}
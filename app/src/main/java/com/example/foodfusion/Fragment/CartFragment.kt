package com.example.foodfusion.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfusion.PayOutActivity
import com.example.foodfusion.adapter.CartAdapter
import com.example.foodfusion.databinding.FragmentCartBinding
import com.example.foodfusion.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodImagesUri: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var userId: String
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        //Database reference to the Firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid?: ""
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCartBinding.inflate(inflater, container, false)


        retrieveCartItems()

        binding.proceedButton.setOnClickListener {
            //Get Order Item Details
            getOrderItemDetails()
        }
        return binding.root
    }

    private fun getOrderItemDetails() {
        val orderRef: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")
        val foodNames = mutableListOf<String>()
        val foodPrice= mutableListOf<String>()
        val foodImage= mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredient = mutableListOf<String>()
        //get item quantity
        val foodQuatities = cartAdapter.getUpdatedItemsQuantities()

        orderRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodsnapshot in snapshot.children) {
                    //get the cart item to respective list
                    val cartitemm = foodsnapshot.getValue(CartItem::class.java)
                    //dd items details in to list
                    cartitemm?.foodName?.let { foodNames.add(it) }
                    cartitemm?.foodPrice?.let { foodPrice.add(it) }
                    cartitemm?.foodImage?.let { foodImage.add(it) }
                    cartitemm?.foodIngredient?.let { foodIngredient.add(it) }
                    cartitemm?.foodDescription?.let { foodDescription.add(it) }
                    cartitemm?.foodQuantity?.let { foodQuatities.add(it) }
                }
                orderNow(
                    foodNames,
                    foodPrice,
                    foodImage,
                    foodIngredient,
                    foodDescription,
                    foodQuatities
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Order Failed Please Try Again",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodImage: MutableList<String>,
        foodDescription: MutableList<String>,
        foodIngredient: MutableList<String>,
        foodQuantities:MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("FoodItemName", foodName as ArrayList<String>)
            intent.putExtra("FoodItemPrice", foodPrice as ArrayList<String>)
            intent.putExtra("FoodItemImage", foodImage as ArrayList<String>)
            intent.putExtra("FoodItemDescription", foodDescription as ArrayList<String>)
            intent.putExtra("FoodItemIngredients", foodIngredient as ArrayList<String>)
            intent.putExtra("FoodItemQuantity", foodQuantities as ArrayList<Int>)
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {

        val foodReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        //list to store the cart items
        foodNames = mutableListOf()
        foodPrices = mutableListOf()
        foodDescriptions = mutableListOf()
        foodIngredients = mutableListOf()
        foodImagesUri = mutableListOf()
        quantity = mutableListOf()

        //Fetch Data from Database
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                foodNames.clear()
                foodPrices.clear()
                foodDescriptions.clear()
                foodIngredients.clear()
                foodImagesUri.clear()
                quantity.clear()
                for (foodsnapshot in snapshot.children) {
                    val cartItems = foodsnapshot.getValue(CartItem::class.java)
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodDescription?.let { foodDescriptions.add(it) }
                    cartItems?.foodIngredient?.let { foodIngredients.add(it) }
                    cartItems?.foodImage?.let { foodImagesUri.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not Fetch", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun setAdapter() {
        cartAdapter = CartAdapter(
            requireContext(),
            foodNames,
            foodPrices,
            foodImagesUri,
            foodDescriptions,
            quantity,
            foodIngredients
        )
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = cartAdapter
        Log.d("CartFragment", "Adapter set successfully")
    }
}



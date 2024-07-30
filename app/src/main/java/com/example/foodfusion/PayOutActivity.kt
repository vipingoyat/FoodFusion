package com.example.foodfusion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodfusion.databinding.ActivityPayOutBinding
import com.example.foodfusion.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayOutBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var name:String
    private lateinit var address:String
    private lateinit var phoneNumber: String
    private lateinit var totalAmount: String
    private lateinit var foodItemName: ArrayList<String>
    private lateinit var foodItemPrice: ArrayList<String>
    private lateinit var foodItemImage: ArrayList<String>
    private lateinit var foodItemDescription: ArrayList<String>
    private lateinit var foodItemIngredients: ArrayList<String>
    private lateinit var foodItemQuantity: ArrayList<Int>

    private lateinit var userId:String
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPayOutBinding.inflate(layoutInflater)



        binding.placeMyOrder.setOnClickListener {
            //get data from textview
            name = binding.payoutName.text.toString().trim()
            address = binding.payoutAddress.text.toString().trim()
            phoneNumber = binding.pauoutPhoneNumber.text.toString().trim()
            if(name.isBlank()||address.isBlank()||phoneNumber.isBlank()){
                Toast.makeText(this,"Please Enter All the Details",Toast.LENGTH_SHORT).show()
            }
            else{
                placeOrder()
            }
        }

        setContentView(binding.root)

        //Initialize the Firebase auth
        auth = FirebaseAuth.getInstance()

        //Initialize the DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().reference

        //Set User Data
        setUserData()

        //Get User data from Firebase
        val intent= intent
        foodItemName= intent.getStringArrayListExtra("FoodItemName") as ArrayList<String>
        foodItemPrice= intent.getStringArrayListExtra("FoodItemPrice") as ArrayList<String>
        foodItemImage= intent.getStringArrayListExtra("FoodItemImage") as ArrayList<String>
        foodItemDescription= intent.getStringArrayListExtra("FoodItemDescription") as ArrayList<String>
        foodItemIngredients= intent.getStringArrayListExtra("FoodItemIngredients") as ArrayList<String>
        foodItemQuantity= intent.getIntegerArrayListExtra("FoodItemQuantity") as ArrayList<Int>

        totalAmount= "Rs " + calculateTotalAmount().toString()
//        binding.totalamount.isEnabled = false
        binding.totalamount.setText(totalAmount)



        binding.backButtonEdit.setOnClickListener {
//            val intent = Intent(this,CartFragment::class.java)
//            startActivity(intent)
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun placeOrder() {
        userId= auth.currentUser?.uid?:""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetails").push().key
        val orderDetails = OrderDetails(userId,name,foodItemName,foodItemPrice,foodItemImage,foodItemQuantity,address,totalAmount,phoneNumber,time,itemPushKey,false,false)
        val orderReference = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {

            val bottomSheetDialog = CongratulationsFragment()
            bottomSheetDialog.show(supportFragmentManager,"Test")
            removeItemfromCart()
            addOrderToHistory(orderDetails)
        }
            .addOnFailureListener {
                Toast.makeText(this,"Failed to Order",Toast.LENGTH_SHORT).show()
            }

    }

    private fun addOrderToHistory(orderDetails: OrderDetails){
        databaseReference.child("user").child(userId).child("Buy History")
            .child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }
    }

    private fun removeItemfromCart() {
        val cartItemsReference = databaseReference.child("user").child(userId).child("CartItems")
        cartItemsReference.removeValue()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for(i in 0 until foodItemPrice.size){
            var price  = foodItemPrice[i]
            val priceIntValue = price.replace("Rs ", "").toInt()
            var quantity = foodItemQuantity[i]
            totalAmount += priceIntValue*quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if(user!=null){
            val userId  = user.uid
            val userRef = databaseReference.child("user").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val names = snapshot.child("name").getValue(String::class.java)?:""
                        val addresses = snapshot.child("address").getValue(String::class.java)?:""
                        val phones = snapshot.child("phone").getValue(String::class.java)?:""
                        binding.apply {
                            payoutName.setText(names)
                            payoutAddress.setText(addresses)
                            pauoutPhoneNumber.setText(phones)
                        }
                    }

                }
                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }


}
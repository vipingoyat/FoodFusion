package com.example.foodfusion.Fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodfusion.R
import com.example.foodfusion.RecentOrderItem
import com.example.foodfusion.adapter.BuyAgainAdapter
import com.example.foodfusion.databinding.FragmentHistoryBinding
import com.example.foodfusion.databinding.FragmentHomeBinding
import com.example.foodfusion.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        //Retrieve and Display the user data
        retrieveBuyHistory()

        binding.receivedButton.setOnClickListener {
            updateDeliveryStatus()
        }


        binding.recentbuyItem.setOnClickListener {
            seeItemRecentBuy()
        }

        return binding.root
    }

    private fun updateDeliveryStatus() {
        val itemPushKey = listOfOrderItem[0].itemPushKey
        val completeOrderReference = database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentRecieved").setValue(true)

    }

    private fun seeItemRecentBuy() {
        listOfOrderItem.firstOrNull()?.let { recentBuy->
            val intent = Intent(requireContext(),RecentOrderItem::class.java)
            intent.putExtra("RecentBuyOrderItems",listOfOrderItem)
            startActivity(intent)

        }
    }

    private fun retrieveBuyHistory() {
        binding.recentbuyItem.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?: ""
        val buyAItemReference: DatabaseReference =
            database.reference.child("user").child(userId).child("Buy History")
        val shortingQuery = buyAItemReference.orderByChild("currentTime")
        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {

                    //display the most recent order details
                    setDataInRecentBuyItem()

                    //setup the recycler view
                    setPreviousBuyItemRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

    private fun setDataInRecentBuyItem() {
        binding.recentbuyItem.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                recentbuyFoodName.text = it.foodNames?.firstOrNull() ?: ""
                recentbuyFoodPrice.text = it.foodPrices?.firstOrNull() ?: ""
                val image = it.foodImages?.firstOrNull() ?: ""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(recentbuyimage)

                val isOrderAccepted = listOfOrderItem[0].OrderAccepted
                if(isOrderAccepted){
                    receiveCard.background.setTint(Color.GREEN)
                    receivedButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setPreviousBuyItemRecyclerView() {
        val buyAgainfoodName = mutableListOf<String>()
        val buyAgainfoodprice = mutableListOf<String>()
        val buyAgainfoodImage = mutableListOf<String>()
        for (i in 1 until listOfOrderItem.size) {
            listOfOrderItem[i].foodNames?.firstOrNull()?.let { buyAgainfoodName.add(it) }
            listOfOrderItem[i].foodPrices?.firstOrNull()?.let { buyAgainfoodprice.add(it) }
            listOfOrderItem[i].foodImages?.firstOrNull()?.let { buyAgainfoodImage.add(it) }
            val rv = binding.buyAgainRecyclerView
            rv.layoutManager = LinearLayoutManager(requireContext())
            buyAgainAdapter = BuyAgainAdapter(
                buyAgainfoodName,
                buyAgainfoodprice,
                buyAgainfoodImage,
                requireContext()
            )
            rv.adapter= buyAgainAdapter
        }

    }
}
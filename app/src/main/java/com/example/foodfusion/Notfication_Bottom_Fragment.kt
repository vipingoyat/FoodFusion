package com.example.foodfusion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfusion.adapter.NotificationAdapter
import com.example.foodfusion.databinding.FragmentNotficationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList

class Notfication_Bottom_Fragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotficationBottomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotficationBottomBinding.inflate(layoutInflater,container, false)
        val notification = listOf("Your order has been Canceled Successfully","Order has been taken by the driver","Congrats Your Order Placed")
        val notificationimages = listOf(
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.succesfull
        )
        val adapter = NotificationAdapter(
            ArrayList(notification),
            ArrayList(notificationimages)
        )
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {
    }
}
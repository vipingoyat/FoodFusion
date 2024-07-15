package com.example.foodfusion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfusion.adapter.MenuAdapter
import com.example.foodfusion.databinding.FragmentMenuBottomsheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class Menu_bottomsheet_fragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomsheetFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomsheetFragmentBinding.inflate(inflater,container,false)

        binding.buttonBack.setOnClickListener{
            dismiss()
        }
        val menuFoodName = listOf("Burger", "Sandwich","Rasmalai","Pizza","Chole Bhature","Gulab Jamun","Subway", "Sandwich","Rasmalai","Pizza","Chole Bhature","Gulab Jamun","Subway")
        val menuItemPrice = listOf("Rs 89","Rs 69", "Rs 59","Rs 129","Rs 79","Rs 18/pc","Rs 139","Rs 69", "Rs 59","Rs 129","Rs 79","Rs 18/pc","Rs 139")
        val menuImage = listOf(
            R.drawable.burger_pic,
            R.drawable.sandwich_pic,
            R.drawable.rasmalai_pic,
            R.drawable.pizza_pic,
            R.drawable.chole_bhature_pic,
            R.drawable.gulab_jamun_pic,
            R.drawable.subway_pic,
            R.drawable.sandwich_pic,
            R.drawable.rasmalai_pic,
            R.drawable.pizza_pic,
            R.drawable.chole_bhature_pic,
            R.drawable.gulab_jamun_pic,
            R.drawable.subway_pic
        )
        val adapter = MenuAdapter(ArrayList(menuFoodName),ArrayList(menuItemPrice), ArrayList(menuImage))
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {
    }
}
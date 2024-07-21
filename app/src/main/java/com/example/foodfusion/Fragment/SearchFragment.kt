package com.example.foodfusion.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.foodfusion.R
import com.example.foodfusion.adapter.MenuAdapter
import com.example.foodfusion.databinding.FragmentSearchBinding
import com.example.foodfusion.databinding.MenuItemBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private val originalFoodName = listOf(
        "Burger", "Sandwich","Rasmalai","Pizza","Chole Bhature","Gulab Jamun","Subway", "Sandwich","Rasmalai","Pizza","Chole Bhature","Gulab Jamun","Subway"
    )
    private val originalMenuItemPrice = listOf("Rs 89","Rs 69", "Rs 59","Rs 129","Rs 79","Rs 18/pc","Rs 139","Rs 69", "Rs 59","Rs 129","Rs 79","Rs 18/pc","Rs 139")
    private val originalMenuImage = listOf(
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private val filteredmenufoodname = mutableListOf<String>()
    private val filteredmenuitemprice = mutableListOf<String>()
    private val filteredmenuitemimage = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        adapter = MenuAdapter(filteredmenufoodname,filteredmenuitemprice,filteredmenuitemimage,requireContext())
        binding.searcRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searcRecyclerView.adapter = adapter

        //set up for Search View
        setupSearchView()


        //show All menus items

        showAllMenu()




        return binding.root
    }

    private fun showAllMenu() {
        filteredmenufoodname.clear()
        filteredmenuitemprice.clear()
        filteredmenuitemimage.clear()


        filteredmenufoodname.addAll(originalFoodName)
        filteredmenuitemprice.addAll(originalMenuItemPrice)
        filteredmenuitemimage.addAll(originalMenuImage)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filtermenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filtermenuItems(newText)
                return true
            }
        })
    }

    private fun filtermenuItems(query: String) {
            filteredmenufoodname.clear()
        filteredmenuitemprice.clear()
        filteredmenuitemimage.clear()


        originalFoodName.forEachIndexed { index, FoodName ->
            if(FoodName.contains(query, ignoreCase = true)){
                filteredmenufoodname.add(FoodName)
                filteredmenuitemprice.add(originalMenuItemPrice[index])
                filteredmenuitemimage.add(originalMenuImage[index])
            }
        }
        adapter.notifyDataSetChanged()
    }

    companion object {

    }
}


package com.example.foodfusion.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfusion.adapter.MenuAdapter
import com.example.foodfusion.databinding.FragmentSearchBinding
import com.example.foodfusion.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var originalMenuItem: MutableList<MenuItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

//        binding.searcRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.searcRecyclerView.adapter = adapter

        //set up for Search View
        setupSearchView()
        //Display and Retrieve Popular menu item
        retrieveAndDisplaySearchItems()

        return binding.root
    }



    private fun retrieveAndDisplaySearchItems() {
        //get reference to the database
        database = FirebaseDatabase.getInstance()
        //get reference to the menu node
        val foodRef: DatabaseReference = database.reference.child("menu")
        originalMenuItem = mutableListOf()
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodsnapshot in snapshot.children){
                    val manuitem = foodsnapshot.getValue(MenuItem::class.java)
                    manuitem?.let {
                        originalMenuItem.add(it) }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun showAllMenu() {
        val fiteredMenuItem = ArrayList(originalMenuItem)
        //Display search item
        setAdapter(fiteredMenuItem)
    }


    private fun setAdapter(fiteredMenuItem: List<MenuItem>) {
        val adapter = MenuAdapter(fiteredMenuItem, requireContext())
        binding.searcRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searcRecyclerView.adapter = adapter
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
        val filteredMenuItem = originalMenuItem.filter {
            it.foodName?.contains(query,ignoreCase = true)==true
        }
        setAdapter(filteredMenuItem)
    }

    companion object {

    }
}


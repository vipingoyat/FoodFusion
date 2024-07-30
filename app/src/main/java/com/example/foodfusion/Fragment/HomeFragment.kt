package com.example.foodfusion.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodfusion.Menu_bottomsheet_fragment
import com.example.foodfusion.R
import com.example.foodfusion.adapter.MenuAdapter
import com.example.foodfusion.databinding.FragmentHomeBinding
import com.example.foodfusion.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItem: MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)


        binding.viewallmenu.setOnClickListener {
            val bottomSheetDialog = Menu_bottomsheet_fragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")
        }

        //Display and Retrieve Popular menu item
        retrieveAndDisplayPopularItems()

        return binding.root

    }

    private fun retrieveAndDisplayPopularItems() {
        //get reference to the database
        database = FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference = database.reference.child("menu")
        menuItem = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( foodsnapshot in snapshot.children){
                    val manuitem = foodsnapshot.getValue(MenuItem::class.java)
                    manuitem?.let {
                        menuItem.add(it) }
                }
                //Display random popular item
                randomPopularItems()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun randomPopularItems() {
        //Create a shuffled list of menu items
        val index = menuItem.indices.toList().shuffled()
        val menuItemtoShow = 6
        val subsetMenu = index.take(menuItemtoShow).map { menuItem[it] }

        setAdapter(subsetMenu)
    }


    private fun setAdapter(subsetMenu:List<MenuItem>) {
        val adapter = MenuAdapter(subsetMenu, requireContext())
        binding.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter = adapter
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.img_2,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner1,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.img_1,ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)

        imageSlider.setItemClickListener(object : ItemClickListener{
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
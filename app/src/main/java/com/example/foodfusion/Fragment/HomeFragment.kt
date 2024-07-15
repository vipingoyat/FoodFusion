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
import com.example.foodfusion.adapter.PopularAdapter
import com.example.foodfusion.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
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
        return binding.root

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
        val foodname = listOf("Burger","Sandwich","Subway","Pizza", "Chole Bhature","Rasmalai", "Gulab Jamun","Burger","Sandwich","Subway","Pizza", "Chole Bhature","Rasmalai", "Gulab Jamun")
        val price = listOf("Rs 89/-", "Rs 69/-", "Rs 139/-", "Rs 129/-","Rs 79/-", "Rs 59/pc","Rs 18/pc","Rs 89/-", "Rs 69/-", "Rs 139/-", "Rs 129/-","Rs 79/-", "Rs 59/pc","Rs 18/pc")
        val popularFoodImages = listOf(
            R.drawable.burger_pic,
            R.drawable.sandwich_pic,
            R.drawable.subway_pic,
            R.drawable.pizza_pic,
            R.drawable.chole_bhature_pic,
            R.drawable.rasmalai_pic,
            R.drawable.gulab_jamun_pic,
            R.drawable.burger_pic,
            R.drawable.sandwich_pic,
            R.drawable.subway_pic,
            R.drawable.pizza_pic,
            R.drawable.chole_bhature_pic,
            R.drawable.rasmalai_pic,
            R.drawable.gulab_jamun_pic)
        val adapter = PopularAdapter(foodname,price,popularFoodImages)
        binding.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter = adapter
    }
    companion object {
    }
}
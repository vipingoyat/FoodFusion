package com.example.foodfusion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodfusion.databinding.FragmentNotficationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
        return binding.root
    }

    companion object {
    }
}
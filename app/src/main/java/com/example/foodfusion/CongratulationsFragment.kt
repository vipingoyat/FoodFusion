package com.example.foodfusion

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodfusion.databinding.FragmentCongratulationsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CongratulationsFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCongratulationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCongratulationsBinding.inflate(layoutInflater,container,false)
        binding.goHomeButton.setOnClickListener {
            val intent = Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {
    }
}
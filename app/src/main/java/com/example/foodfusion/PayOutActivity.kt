package com.example.foodfusion

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.foodfusion.Fragment.CartFragment
import com.example.foodfusion.databinding.ActivityPayOutBinding
import com.example.foodfusion.databinding.FragmentCartBinding
import com.example.foodfusion.databinding.PopularItemListBinding

class PayOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        binding.placeMyOrder.setOnClickListener {
            val bottomSheetDialog = CongratulationsFragment()
            bottomSheetDialog.show(supportFragmentManager,"Test")
        }

        setContentView(binding.root)
        binding.backButtonEdit.setOnClickListener {
            // Get the fragment manager
            val fragmentManager = supportFragmentManager
            // Create a new transaction
            val transaction = fragmentManager.beginTransaction()
            // Replace the current fragment container with the CartFragment
            transaction.replace(R.id.main, CartFragment()) // Replace R.id.fragment_container with your actual container ID
            // Commit the transaction
            transaction.commit()
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
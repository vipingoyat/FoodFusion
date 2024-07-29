package com.example.foodfusion.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodfusion.R
import com.example.foodfusion.databinding.FragmentProfileBinding
import com.example.foodfusion.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)


        setUserData()

        //Edit Profile Button
        binding.apply {
            profileName.isEnabled = false
            profileAddress.isEnabled = false
            profileEmail.isEnabled = false
            profilePhone.isEnabled = false
        }
        binding.button5.setOnClickListener {
            binding.apply {
                profileName.isEnabled = !profileName.isEnabled
                profileAddress.isEnabled = !profileName.isEnabled
                profileEmail.isEnabled = !profileName.isEnabled
                profilePhone.isEnabled = !profileName.isEnabled
            }
        }

        //Save Information Button
        binding.button4.setOnClickListener {
            val name = binding.profileName.text.toString()
            val address = binding.profileAddress.text.toString()
            val email = binding.profileEmail.text.toString()
            val phone = binding.profilePhone.text.toString()

            updateUserData(name, address, email, phone)
        }



        return binding.root
    }

    private fun updateUserData(name: String, address: String, email: String, phone: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("user").child(userId)
            val userData = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT)
                    .show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Profile Updated Failed", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }


    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userProfile = snapshot.getValue(UserData::class.java)
                        if (userProfile != null) {
                            binding.profileName.setText(userProfile.name)
                            binding.profileAddress.setText(userProfile.address)
                            binding.profileEmail.setText(userProfile.email)
                            binding.profilePhone.setText(userProfile.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}
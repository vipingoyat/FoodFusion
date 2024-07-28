package com.example.foodfusion.model

import android.location.Address
import android.provider.ContactsContract.CommonDataKinds.Email

data class UserData(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phone: String? = null,
    val address: String? = null
)

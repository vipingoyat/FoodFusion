package com.example.foodfusion

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodfusion.databinding.ActivityLoginBinding
import com.example.foodfusion.databinding.ActivitySigninBinding
import com.example.foodfusion.model.UserData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SigninActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var name:String
    private lateinit var email:String
    private lateinit var password: String
    private lateinit var googleSignInClient: GoogleSignInClient


    private val binding: ActivitySigninBinding by lazy{
        ActivitySigninBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("342104382137-07tsfdnmc8f4abb7vto2llrqd375pf3k.apps.googleusercontent.com")
            .requestEmail()
            .build()

        //Firebase initialisation
        auth = Firebase.auth

        //Database Initialization
        database = Firebase.database.reference

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)

        binding.alreadyhavebutton.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.GoogleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }


        binding.createaccountbutton.setOnClickListener {
            name = binding.nameEditText.text.toString()
            email = binding.editTextEmail.text.toString().trim()
            password = binding.editTextTextPassword.text.toString().trim()

            if(name.isBlank()||email.isEmpty()||password.isBlank()){
                Toast.makeText(this,"Please Fill All the Details",Toast.LENGTH_SHORT).show()
            }
            else{
                createAccount(email,password)
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task->
            if(task.isSuccessful){
                Toast.makeText(this, "Account Created Successfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this, "Account Creation Failed",Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure",task.exception)
            }
        }
    }

    private fun saveUserData() {
        //Take the data from Edit Text
        name = binding.nameEditText.text.toString()
        email = binding.editTextEmail.text.toString().trim()
        password = binding.editTextTextPassword.text.toString().trim()
        val user = UserData(name, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        //save user data into Firebase Database
        database.child("user").child(userId).setValue(user)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->    //lemda with name Result
        if(result.resultCode==Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful) {
                val account:GoogleSignInAccount = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { authtask->
                    if(authtask.isSuccessful){
                        Toast.makeText(this, "Successfull SignIn with Google",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Google SignIn Failed",Toast.LENGTH_SHORT).show()
                        Log.d("Account1","createAccount: Failure",task.exception)
                    }
                }
            }
            else{
                Toast.makeText(this, "Google SignIn Failed",Toast.LENGTH_SHORT).show()
                Log.d("GoogleSignIn", "Google signIn failed", task.exception)
            }
        }
        else{
            Toast.makeText(this, "Google SignIn Failed",Toast.LENGTH_SHORT).show()

        }

    }
}
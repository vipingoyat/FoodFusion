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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api.Client
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import java.util.zip.Inflater

class LoginActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database:DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth


    private val binding: ActivityLoginBinding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("342104382137-07tsfdnmc8f4abb7vto2llrqd375pf3k.apps.googleusercontent.com")
            .requestEmail()
            .build()
        //Firebase Initialisation
        auth = Firebase.auth

        //Database Initialisation
        database = Firebase.database.reference

        //Initial Google SignIn
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        binding.button2.setOnClickListener {   //Google Button
            val signinIntent = googleSignInClient.signInIntent
            launcher.launch(signinIntent)
        }





        binding.Loginbutton.setOnClickListener {
            //get text from Edit Text
            email = binding.editTextTextEmailAddress.text.toString().trim()
            password = binding.editTextTextPassword.text.toString().trim()
            if(email.isBlank()){
                Toast.makeText(this, "Please Enter the Email", Toast.LENGTH_SHORT).show()
            }
            if(password.isBlank()){
                Toast.makeText(this, "Please Enter the Password", Toast.LENGTH_SHORT).show()
            }
            else {
                verifyUserAccount(email, password)
            }
        }




        binding.donthavebutton.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,systemBars.left)
            insets
        }
    }




    private fun verifyUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(this, "LogIn Successfull", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this, "Please Enter Correct Email and Password", Toast.LENGTH_SHORT).show()
            }
        }
    }





    //For Google SignIn Launcher
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->    //lemda with name Result
        if(result.resultCode== Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful) {
                val account: GoogleSignInAccount = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { authtask->
                    if(authtask.isSuccessful){
                        Toast.makeText(this, "Successfull SignIn with Google 🥳", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Google SignIn Failed 🥲", Toast.LENGTH_SHORT).show()
                        Log.d("Account1","createAccount: Failure",task.exception)
                    }
                }
            }
            else{
                Toast.makeText(this, "Google SignIn Failed", Toast.LENGTH_SHORT).show()
                Log.d("GoogleSignIn", "Google signIn failed", task.exception)
            }
        }
        else{
            Toast.makeText(this, "Google SignIn Failed", Toast.LENGTH_SHORT).show()

        }
    }
}
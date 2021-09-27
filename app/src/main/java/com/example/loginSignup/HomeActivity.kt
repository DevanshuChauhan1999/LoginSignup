package com.example.loginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.loginSignup.databinding.ActivityHomeBinding
//import com.example.minorattempt1.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivityHomeBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure Actionbar
        actionBar = supportActionBar!!
        actionBar.title="Home"

        //ini firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //logout
        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }

    }

    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null)
        {
            //user not null user is logged in, get user info
            val email = firebaseUser.email
            // set to text view
            binding.emailTv.text=email
        }
        else
        {
            //user is null, user not logged in
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}
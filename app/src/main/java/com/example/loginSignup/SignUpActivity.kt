package com.example.loginSignup

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.loginSignup.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivitySignUpBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //configure Actionbar
        actionBar = supportActionBar!!
        actionBar.title="SignUp"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)


        //ini firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click, begin signup
        binding.signupButton.setOnClickListener{
            //validate data
            validateData()
        }

    }

    private fun validateData() {
        //get data
        email = binding.email.text.toString().trim()
        password =binding.Password.text.toString().trim()

        //validate data
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //invalid email
                    binding.email.error = "Invalid email format"
                }

                else if (TextUtils.isEmpty(password)){
                    //no password entered
                    binding.passwordBox.error="Please enter password"
                }

                else if(password.length<6)
                {
                    binding.Password.error="Password must be atleast 6 character long"
                }

                else{
                    //data is validated, begin login
                    firebaseSignup()
                }
    }

    private fun firebaseSignup() {
         //show progress
        progressDialog.show()

        //create account

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email",Toast.LENGTH_SHORT).show()

                //open home
                startActivity(Intent(this,HomeActivity::class.java))
                finish()

            }
            .addOnFailureListener{e->
                //signup failed
                progressDialog.dismiss()
                Toast.makeText(this, "Signup failed due to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go to previous activity, when back button of action bar is clicked
        return super.onSupportNavigateUp()
    }



}
package com.genie.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.genie.R
import android.view.View
import com.genie.MainActivity
import com.genie.databinding.ActivityAccountCreationBinding

class AccountCreationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAccountCreationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginAlreadyLoginTv.setOnClickListener {
            binding.loginLayout.visibility = View.GONE
            binding.signupLayout.visibility = View.VISIBLE
        }
        binding.signupAlreadySignupTv.setOnClickListener {
            binding.loginLayout.visibility = View.VISIBLE
            binding.signupLayout.visibility = View.GONE
        }
        binding.loginLoginBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
    private fun ac_login(email:String,password:String){}
    private fun ac_signup(username:String,email:String,password:String){}
}
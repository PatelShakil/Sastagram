package com.genie.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.genie.R
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.genie.MainActivity
import com.genie.databinding.ActivityAccountCreationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class AccountCreationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAccountCreationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        if(auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding.loginAlreadyLoginTv.setOnClickListener {
            binding.loginLayout.visibility = View.GONE
            binding.signupLayout.visibility = View.VISIBLE
        }
        binding.signupAlreadySignupTv.setOnClickListener {
            binding.loginLayout.visibility = View.VISIBLE
            binding.signupLayout.visibility = View.GONE
        }
        binding.signupSignupBtn.setOnClickListener {
            var check = false
            database.reference
                .child("users")
                .addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            for (snapshot1 in snapshot.children){
                                var user = snapshot1.getValue(UserModel::class.java)!!
                                if(user.username.toString() == binding.signupUsernameEt.text.toString()){
                                    check = true
                                    break
                                }
                            }
                            if (!check){
                                if (binding.signupEmailEt.text.isNotEmpty()&&binding.signupPasswordConfirmEt.toString().isNotEmpty()){
                                    ac_signup(binding.signupUsernameEt.text.toString(),binding.signupEmailEt.text.toString(),binding.signupPasswordConfirmEt.text.toString())
                                    binding.signupUsernameEt.visibility = View.GONE
                                }
                            }
                            else if(check)
                                binding.signupUsernameEt.error = "Enter Unique Username"
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })

        }
        binding.loginLoginBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        if (binding.loginEmailEt.text.isNotEmpty()&&binding.loginPasswordEt.text.isNotEmpty()){
            ac_login(binding.loginEmailEt.text.toString(),binding.loginPasswordEt.text.toString())
        }
    }
private fun ac_login(email:String,password:String){
    auth.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener {
            if (it.isSuccessful){
                startActivity(Intent(this,MainActivity::class.java))
                Toast.makeText(this,"Welcome back to Genie",Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this,it.exception?.message,Toast.LENGTH_SHORT).show()
        }
}
private fun ac_signup(username:String,email:String,password:String){
    auth.createUserWithEmailAndPassword(email,password)
        .addOnCompleteListener {
            if(it.isSuccessful){
                database.reference
                    .child("users")
                    .child(auth.uid.toString())
                    .setValue(UserModel(username.trim(),email,password,username))
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(this,MainActivity::class.java))
                            Toast.makeText(this,"Welcome to Genie",Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(this,it.exception?.message,Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this,it.exception?.message,Toast.LENGTH_SHORT).show()
            }
        }
}
}

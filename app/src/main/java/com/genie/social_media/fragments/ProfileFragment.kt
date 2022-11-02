package com.genie.social_media.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genie.R
import com.genie.account.UserModel
import com.genie.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {
    lateinit var binding:FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.root.minWidth = ViewGroup.MarginLayoutParams.MATCH_PARENT
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        database.reference.child("users")
            .child(auth.uid.toString())
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var user = snapshot.getValue(UserModel::class.java)
                        if (user!=null){
                            binding.userMyProfileUserName.text = user.name
                            binding.userMyProfileBio.text = snapshot.child("bio").value.toString()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        binding.myProfileEditProfileBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_container,EditProfileFragment(),"edit_profile").addToBackStack("edit profile").commit()
        }
        binding.addPost.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_container,AddPostFragment(),"add_post").addToBackStack("add_post").commit()
        }
        return binding.root
    }
}
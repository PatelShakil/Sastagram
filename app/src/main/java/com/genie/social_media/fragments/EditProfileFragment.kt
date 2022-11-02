package com.genie.social_media.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genie.R
import com.genie.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class EditProfileFragment : Fragment() {
    lateinit var binding: FragmentEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        database.reference.child("users")
            .child(auth.uid.toString())
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        binding.username.text = snapshot.child("username").value.toString()
                        binding.editName.setText(snapshot.child("name").value.toString())
                        binding.editBio.setText(snapshot.child("bio").value.toString())
                        binding.editDob.setText(snapshot.child("dob").value.toString())
                        binding.editEmail.text = snapshot.child("email").value.toString()
                        binding.editPhone.setText(snapshot.child("phone").value.toString())
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


        return binding.root
    }
}
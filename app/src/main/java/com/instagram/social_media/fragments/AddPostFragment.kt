package com.instagram.social_media.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.instagram.R
import com.instagram.databinding.FragmentAddPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.instagram.account.Constants
import java.util.*
import com.instagram.social_media.models.PostModel
import java.io.InputStream

class AddPostFragment : Fragment() {
    lateinit var binding:FragmentAddPostBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var encodedpost:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPostBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 75)
        binding.addPostPic.setOnClickListener{
            startActivityForResult(intent,75)
        }
        binding.postUploadBtn.setOnClickListener {
            if (binding.postCaption.text?.isNotEmpty()!!){
                if (encodedpost.isNotEmpty()) {
                    database.reference.child("social_media").child("posts")
                        .child(database.reference.push().key.toString())
                        .setValue(
                            PostModel(binding.postCaption.text.toString(),
                                auth.uid.toString(),
                                encodedpost,
                                Date().time,
                                0
                            )
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(context, "Post uploaded", Toast.LENGTH_SHORT).show()
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.main_container, ProfileFragment(), "profile")
                                    .addToBackStack("profile").commit()
                            }
                        }
                }
                else{
                    Toast.makeText(context,"Please select post",Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            try {
                var inputStream = context?.contentResolver?.openInputStream(data.data!!)
                var bitmap = BitmapFactory.decodeStream(inputStream)
                binding.addPostPic.setImageBitmap(bitmap)
                encodedpost = Constants().encodeImage(bitmap)
            }catch (e:Exception){
                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
            }
        }
    }
}
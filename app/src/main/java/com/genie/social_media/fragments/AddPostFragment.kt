package com.genie.social_media.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.genie.R
import com.genie.databinding.FragmentAddPostBinding
import com.genie.social_media.PostModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddPostFragment : Fragment() {
    lateinit var binding:FragmentAddPostBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var post_url:Uri
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
                var sr = storage.reference.child("posts/"+auth.uid+ Date().date +".jpg")
                    sr.putFile(post_url)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            sr.downloadUrl
                                .addOnSuccessListener {uri ->
                                    var post = PostModel(uri.toString(),Date().time,binding.postCaption.text.toString(),auth.uid.toString())
                                    database.reference
                                        .child("social_media")
                                        .child("posts")
                                        .child(database.reference.push().key.toString())
                                        .setValue(post)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful){
                                                Toast.makeText(context,"Post uploaded",Toast.LENGTH_SHORT).show()
                                                parentFragmentManager.beginTransaction().replace(R.id.main_container,ProfileFragment(),"profile").addToBackStack("profile").commit()
                                            }
                                        }
                                }
                        }
                    }
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            binding.addPostPic.setImageURI(data.data)
            post_url = data.data!!
        }
    }
}
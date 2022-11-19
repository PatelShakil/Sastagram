package com.instagram.social_media.fragments

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.VirtualLayout
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.instagram.R
import com.instagram.databinding.FragmentViewUserProfileBinding
import com.instagram.account.Constants

class ViewUserProfileFragment : Fragment() {
    lateinit var binding: FragmentViewUserProfileBinding
    var db = FirebaseFirestore.getInstance()
    var database = FirebaseDatabase.getInstance()
    var auth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewUserProfileBinding.inflate(layoutInflater)

        var user_view : String = arguments?.getString("uid").toString()
        db.collection(com.instagram.account.Constants().KEY_COLLECTION_USERS)
            .document(user_view)
            .addSnapshotListener { value, error ->
                if (error != null)
                    return@addSnapshotListener
                if (value != null){
                    binding.userMyProfileUserName.text = value["name"].toString()
                    binding.userMyProfileBio.text = value["bio"].toString()
                    Glide.with(binding.viewUserProfile.context)
                        .load(value["profile_pic"].toString())
                        .placeholder(R.drawable.profile_icon)
                        .into(binding.viewUserProfilePic)
                }
            }
        database.reference.child("users")
            .child(user_view)
            .child(com.instagram.account.Constants().KEY_FOLLOWERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var list = ArrayList<String>()
                    if (snapshot.exists()){
                        for (i in snapshot.children){
                            list.add(i.key.toString())
                        }
                        binding.followerCount.text = list.size.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {                }
            })
        database.reference.child("users")
            .child(user_view)
            .child(com.instagram.account.Constants().KEY_FOLLOWING)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var list = ArrayList<String>()
                    if (snapshot.exists()){
                        for (i in snapshot.children){
                            list.add(i.key.toString())
                        }
                        binding.followingCount.text = list.size.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {                }
            })
        var per_post_list = ArrayList<String>()
        database.reference.child("social_media")
            .child("posts")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for(snapshot1 in snapshot.children){
                            if (snapshot1.child("post_author").value.toString() == user_view){
                                per_post_list.add(snapshot1.child("post_url").value.toString())
                            }
                        }
                        binding.postCount.text = per_post_list.size.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        database.reference.child("users")
            .child(auth.uid.toString())
            .child(Constants().KEY_FOLLOWING)
            .child(user_view)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        binding.viewFollowBtn.text = "Following"
                        binding.viewFollowBtn.width = LayoutParams.WRAP_CONTENT
                        binding.viewMessageBtn.width = LayoutParams.MATCH_PARENT
                        binding.viewFollowBtn.setBackgroundColor(R.drawable.following_btn_background)
                    }
                    else{
                        binding.viewFollowBtn.text = "Follow"
                        binding.viewFollowBtn.width = 250
                        binding.viewMessageBtn.width = LayoutParams.WRAP_CONTENT
                        binding.viewFollowBtn.setBackgroundColor(R.drawable.follow_btn_background)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        return binding.root
    }
}
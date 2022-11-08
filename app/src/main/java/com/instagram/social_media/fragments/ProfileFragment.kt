package com.instagram.social_media.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.instagram.R
import com.instagram.account.UserModel
import com.instagram.databinding.FragmentProfileBinding
import com.instagram.social_media.adapters.ProfilePostAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.instagram.account.Constants
import com.instagram.databinding.SampleSocialPostProfileBinding
import com.instagram.social_media.models.PostModel

class ProfileFragment : Fragment() {
    lateinit var binding:FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var db :FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        db = FirebaseFirestore.getInstance()
        database.reference.keepSynced(true)
                db.collection(Constants().KEY_COLLECTION_USERS)
                    .document(auth.uid.toString())
                    .get()
                    .addOnSuccessListener {
                        if (it.exists()){
                            binding.userMyProfileUserName.text = it["name"].toString()
                            Glide.with(context?.applicationContext!!)
                                .load(it["profile_pic"].toString())
                                .placeholder(R.drawable.profile_icon)
                                .into(binding.userMyProfilePic)
                            binding.userMyProfileBio.text = it["bio"].toString()
                        }
                    }
        binding.myProfileEditProfileBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_container,EditProfileFragment(),"edit_profile").addToBackStack("edit profile").commit()
        }
        binding.addPost.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_container,AddPostFragment(),"add_post").addToBackStack("add_post").commit()
        }
        var post_list = ArrayList<String>()
        var post_adapter = ProfilePostAdapter(context?.applicationContext!!,post_list)
        binding.uploadedPostRv.adapter = post_adapter
        database.reference.child("social_media")
            .child("posts")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for(snapshot1 in snapshot.children){
                            if (snapshot1.child("post_author").value.toString() == auth.uid.toString()){
                                post_list.add(0,snapshot1.child("post_url").value.toString())
                            }
                        }
                        post_adapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        var query : Query = database.reference.child("social_media").child("posts")
            .orderByChild("post_author").equalTo(auth.uid)
        val options: FirebaseRecyclerOptions<PostModel> = FirebaseRecyclerOptions.Builder<PostModel>()
            .setQuery(query,PostModel::class.java)
            .build()
        var adapter = object : FirebaseRecyclerAdapter<PostModel,ProfilePostViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePostViewHolder {
                return ProfilePostViewHolder(
                    LayoutInflater.from(context?.applicationContext)
                        .inflate(R.layout.sample_social_post_profile, parent, false)
                )
            }

            override fun onBindViewHolder(
                holder: ProfilePostViewHolder,
                position: Int,
                model: PostModel
            ) {                 holder.binding.postUploaded.visibility = View.VISIBLE
                    holder.binding.postUploaded.setImageBitmap(Constants().decodeImage(model.post_url))
                    holder.binding.postUploaded.setOnClickListener {
                        var fg = PostViewFragment()
                        var bundle = Bundle()
                        bundle.putString("post_url",model.post_url)
                        fg.arguments = bundle
                        parentFragmentManager.beginTransaction().replace(R.id.main_container,fg,"post_view").addToBackStack("post_view").commit()
                    }
            }
        }
        binding.uploadedPostRv.adapter = adapter
        adapter.startListening()
        return binding.root
    }
    class ProfilePostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: SampleSocialPostProfileBinding
        init {
            binding = SampleSocialPostProfileBinding.bind(itemView)
        }
    }
}
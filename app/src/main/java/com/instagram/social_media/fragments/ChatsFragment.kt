package com.instagram.social_media.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.instagram.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.instagram.account.Constants
import com.instagram.account.Follower
import com.instagram.account.UserModel
import com.instagram.databinding.FragmentChatsBinding
import com.instagram.databinding.SampleSocialUserBinding
import com.instagram.social_media.ChatActivity
import com.instagram.social_media.adapters.ActiveUsersAdapter
import com.instagram.social_media.adapters.ChatUsersAdapter
import java.util.*
import kotlin.collections.ArrayList


class ChatsFragment : Fragment() {
    lateinit var binding:FragmentChatsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    lateinit var db:FirebaseFirestore
    lateinit var users_list:ArrayList<UserModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        db = FirebaseFirestore.getInstance()
        users_list = ArrayList<UserModel>()
        db.collection(Constants().KEY_COLLECTION_USERS)
            .whereNotEqualTo("uid", auth.uid.toString())
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (i in it.documents) {
                        var user: UserModel = i.toObject(UserModel::class.java)!!
                        user.profile_pic = i["profile_pic"].toString()
                        users_list.add(user)
                    }
                }
                if (users_list.size > 0) {
                    var users_adapter = ChatUsersAdapter(context?.applicationContext!!, users_list)
                    binding.chatUsersRv.adapter = users_adapter
                    users_adapter.notifyDataSetChanged()
                }
            }
        return binding.root
    }


}
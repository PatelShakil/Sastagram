package com.genie.social_media.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.genie.databinding.FragmentHomeBinding
import com.genie.social_media.adapters.PostAdapter
import com.genie.social_media.PostModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    lateinit var binding :FragmentHomeBinding
    lateinit var database:FirebaseDatabase
    lateinit var post_list:ArrayList<PostModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        database = FirebaseDatabase.getInstance()
        post_list = ArrayList()
        var adapter = PostAdapter(context?.applicationContext!!,post_list)
        binding.feedPostRv.adapter = adapter
        database.reference.child("social_media")
            .child("posts")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    post_list.clear()
                    if (snapshot.exists()){
                        for (i in snapshot.children){
                            var post: PostModel? =i.getValue(PostModel::class.java)
                            post?.post_id = i.key.toString()
                            if (post != null) {
                                post_list.add(0,post)
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        return binding.root
    }
}
package com.genie.social_media.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genie.R
import com.genie.databinding.FragmentPostViewBinding
import com.genie.social_media.PostModel
import com.genie.social_media.adapters.PostAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostViewFragment : Fragment() {
    lateinit var binding:FragmentPostViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentPostViewBinding.inflate(layoutInflater)
        var bundle = this.arguments
        if (bundle!=null){
            var post_list = ArrayList<PostModel>()
            var post_adapter = PostAdapter(context?.applicationContext!!,post_list)
            binding.postRv.adapter = post_adapter
            FirebaseDatabase.getInstance().reference
                .child("social_media")
                .child("posts")
                .addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        post_list.clear()
                        if (snapshot.exists()){
                            for (snapshot1 in snapshot.children){
                                if (snapshot1.child("post_url").value == bundle.getBundle("post_url"))
                                    post_list.add(snapshot1.getValue(PostModel::class.java)!!)
                            }
                            post_adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
        return binding.root
    }
}
package com.instagram.social_media.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.instagram.databinding.FragmentHomeBinding
import com.instagram.social_media.adapters.PostAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.instagram.R
import com.instagram.account.Constants
import com.instagram.social_media.models.PostModel


class HomeFragment : Fragment() {
    lateinit var binding :FragmentHomeBinding
    lateinit var database:FirebaseDatabase
    lateinit var post_list:ArrayList<PostModel>
    lateinit var adapter :PostAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        database = FirebaseDatabase.getInstance()
        post_list = ArrayList()
        post_list.clear()
        adapter = PostAdapter(context?.applicationContext!!,post_list)

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
        val options: FirebaseRecyclerOptions<PostModel> = FirebaseRecyclerOptions.Builder<PostModel>()
            .setQuery(database.reference,PostModel::class.java)
            .build()
        var adapter = object : FirebaseRecyclerAdapter<PostModel, ProfileFragment.ProfilePostViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileFragment.ProfilePostViewHolder {
                return ProfileFragment.ProfilePostViewHolder(
                    LayoutInflater.from(context?.applicationContext)
                        .inflate(R.layout.sample_social_post_profile, parent, false)
                )
            }

            override fun onBindViewHolder(
                holder: ProfileFragment.ProfilePostViewHolder,
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

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}
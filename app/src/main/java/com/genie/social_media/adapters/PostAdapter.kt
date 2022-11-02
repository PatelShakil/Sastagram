package com.genie.social_media.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.genie.R
import com.genie.databinding.SampleSocialPostBinding
import com.genie.social_media.PostModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostAdapter:RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    var context:Context
    var post_list:ArrayList<PostModel>
    constructor(context: Context, post_list: ArrayList<PostModel>) : super() {
        this.context = context
        this.post_list = post_list
    }
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding:SampleSocialPostBinding
        init {
            binding = SampleSocialPostBinding.bind(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.sample_social_post,null,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        var post = post_list[position]
        holder.binding.userPostCaption.text = post.post_caption
        Glide.with(context).load(post.post_url).placeholder(R.drawable.image_icon).into(holder.binding.userPost)
        database.reference.child("users")
            .child(post.post_author)
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        holder.binding.userName.text=snapshot.child("name").value.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    override fun getItemCount(): Int {
        return post_list.size
    }
}
package com.instagram.social_media.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.*
import com.instagram.R
import com.instagram.account.Constants
import com.instagram.databinding.SampleSocialPostBinding
import com.instagram.social_media.models.PostModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class PostAdapter:RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    var context:Context
    var post_list:ArrayList<PostModel>
    constructor(context: Context, post_list: ArrayList<PostModel>) : super() {
        this.context = context
        this.post_list = post_list
    }
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()
    var auth = FirebaseAuth.getInstance()

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
        var db = FirebaseFirestore.getInstance()
        var post_author_name = ""
        var curren_user_name = ""
        holder.binding.userPostCaption.text = post.post_caption
        holder.binding.userPost.setImageBitmap(Constants().decodeImage(post.post_url))
        db.collection(Constants().KEY_COLLECTION_USERS)
            .document(post.post_author)
            .get()
            .addOnSuccessListener {
                if (it.exists()){
                    holder.binding.userName.text = it["name"].toString()
                    post_author_name = it["name"].toString()
                    Glide.with(context.applicationContext!!)
                        .load(it["profile_pic"])
                        .placeholder(R.drawable.profile_icon)
                        .into(holder.binding.userProfile)
                }
            }
        db.collection(Constants().KEY_COLLECTION_USERS)
            .document(FirebaseAuth.getInstance().uid.toString())
            .get()
            .addOnSuccessListener {
                if (it.exists()){
                    curren_user_name = it["name"].toString()
                    Glide.with(context.applicationContext!!)
                        .load(it["profile_pic"])
                        .placeholder(R.drawable.profile_icon)
                        .into(holder.binding.currentUserProfile)
                }
            }
    }

    override fun getItemCount(): Int {
        return post_list.size
    }
}
package com.instagram.social_media.adapters

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
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
import com.instagram.social_media.BaseActivity
import com.instagram.social_media.SocialMediaActivity
import com.instagram.social_media.models.PostModel
import java.util.*


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
//        holder.binding.userPostCaption.text = post.post_caption
//        if (post.post_url.contains("https://")) {
//            try {
////                holder.binding.userPostVideo.setVideoURI(Uri.parse(post.post_url))
//               holder.binding.userPostVideo.visibility = View.VISIBLE
//               holder.binding.userPost.visibility = View.GONE
////                holder.binding.userPostVideo.setMediaController(MediaController(context))
////                holder.binding.userPostVideo.start()
//            }catch (e:Exception) {
//                Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
//            }
//        }else {
//            holder.binding.userPost.visibility = View.VISIBLE
//            holder.binding.userPostVideo.visibility = View.VISIBLE
//            holder.binding.userPost.setImageBitmap(Constants().decodeImage(post.post_url))
//        }
//        db.collection(Constants().KEY_COLLECTION_USERS)
//            .document(post.post_author)
//            .get()
//            .addOnSuccessListener {
//                if (it.exists()) {
//                    holder.binding.userName.text = it["name"].toString()
//                    post_author_name = it["name"].toString()
//                    Glide.with(context.applicationContext!!)
//                        .load(it["profile_pic"])
//                        .placeholder(R.drawable.profile_icon)
//                        .into(holder.binding.userProfile)
//                }
//            }
//        db.collection(Constants().KEY_COLLECTION_USERS)
//            .document(FirebaseAuth.getInstance().uid.toString())
//            .get()
//            .addOnSuccessListener {
//                if (it.exists()) {
//                    curren_user_name = it["name"].toString()
//                    Glide.with(context.applicationContext!!)
//                        .load(it["profile_pic"])
//                        .placeholder(R.drawable.profile_icon)
//                        .into(holder.binding.currentUserProfile)
//                }
//            }
//        holder.binding.postLikeCount.text = post.post_like.toString()
//        database.reference.child("social_media")
//            .child("posts")
//            .child(post.post_id)
//            .child("likes")
//            .child(auth.uid.toString())
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        holder.binding.postLikeBtn.setBackgroundResource(R.drawable.ic_like_red)
//                    } else
//                        holder.binding.postLikeBtn.setBackgroundResource(R.drawable.ic_like)
//                }
//
//                override fun onCancelled(error: DatabaseError) {}
//
//            })
//        holder.binding.postLikeBtn.setOnClickListener {
//            database.reference.child("social_media")
//                .child("posts")
//                .child(post.post_id)
//                .child("likes")
//                .child(auth.uid.toString())
//                .addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if (snapshot.exists()) {
//                            database.reference.child("social_media")
//                                .child("posts")
//                                .child(post.post_id)
//                                .child("likes")
//                                .child(auth.uid.toString())
//                                .setValue(null)
//                                .addOnSuccessListener {
//                                    database.reference.child("social_media")
//                                        .child("posts")
//                                        .child(post.post_id)
//                                        .child("post_like")
//                                        .setValue(post.post_like - 1)
//                                        .addOnSuccessListener {
//                                            holder.binding.postLikeBtn.setBackgroundResource(R.drawable.ic_like)
//                                        }
//                                }
//                        } else {
//                            database.reference.child("social_media")
//                                .child("posts")
//                                .child(post.post_id)
//                                .child("likes")
//                                .child(auth.uid.toString())
//                                .setValue(Date().time)
//                                .addOnSuccessListener {
//                                    database.reference.child("social_media")
//                                        .child("posts")
//                                        .child(post.post_id)
//                                        .child("post_like")
//                                        .setValue(post.post_like + 1)
//                                        .addOnSuccessListener {
//                                            holder.binding.postLikeBtn.setBackgroundResource(R.drawable.ic_like_red)
//                                        }
//                                }
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {}
//
//                })
//
//        }
//        holder.binding.addYourComment.doOnTextChanged { text, start, before, count ->
//            if (text != null && text.isNotEmpty())
//                holder.binding.sendPostComment.visibility = View.VISIBLE
//            else
//                holder.binding.sendPostComment.visibility = View.GONE
//        }
//        database.reference.child("users")
//            .child(auth.uid.toString())
//            .child("saved")
//            .child(post.post_id)
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        holder.binding.postSave.setBackgroundResource(R.drawable.saved)
//                    }
//                }
//                override fun onCancelled(error: DatabaseError) {}
//            })
//        holder.binding.postSave.setOnClickListener {
//            database.reference.child("users")
//                .child(auth.uid.toString())
//                .child("saved")
//                .child(post.post_id)
//                .addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if (snapshot.exists()) {
//                            database.reference.child("users")
//                                .child(auth.uid.toString())
//                                .child("saved")
//                                .child(post.post_id)
//                                .setValue(null)
//                                .addOnSuccessListener {
//                                    holder.binding.postSave.setBackgroundResource(R.drawable.save_outline)
//                                }
//                        } else {
//                            database.reference.child("users")
//                                .child(auth.uid.toString())
//                                .child("saved")
//                                .child(post.post_id)
//                                .setValue(Date().time)
//                                .addOnSuccessListener {
//                                    holder.binding.postSave.setBackgroundResource(R.drawable.saved)
//                                }
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {}
//                })
//        }
    }




    override fun getItemCount(): Int {
        return post_list.size
    }
}
package com.instagram.social_media.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.instagram.R
import com.instagram.account.Constants
import com.instagram.databinding.SampleConversionUserBinding
import com.instagram.social_media.ChatActivity
import com.instagram.social_media.models.ChatModel

class RecentConversionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{
    var msglist:List<ChatModel>
    constructor(msglist: List<ChatModel>) {
        this.msglist = msglist
    }

    class RecentConViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var binding:SampleConversionUserBinding
        init {
            binding = SampleConversionUserBinding.bind(itemView)
        }
        fun setData(con:ChatModel){
            var profile_pic:String
            FirebaseFirestore.getInstance().collection(Constants().KEY_COLLECTION_USERS)
                .document(con.conversionid)
                .addSnapshotListener{value,error ->
                    if(error != null)
                        return@addSnapshotListener
                    if (value != null){
                        binding.username.text = value.getString(Constants().KEY_NAME)
                        Glide.with(binding.profile.context)
                            .load(value.getString("profile_pic"))
                            .placeholder(R.drawable.profile_icon)
                            .into(binding.profile)
                        profile_pic = value.getString("profile_pic").toString()
                        binding.socialUser.setOnClickListener {
                            var intent = Intent(binding.socialUser.context, ChatActivity::class.java)
                            var bundle= Bundle()
                            bundle.putString("uid",con.conversionid)
                            bundle.putString("profile_pic",profile_pic)
                            intent.putExtras(bundle)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            binding.socialUser.context.startActivity(intent)
                        }
                    }
                }
            binding.lastMsg.text = con.message
            binding.msgTime.text = ChatActivity().readableDate(con.date)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecentConViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_conversion_user,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        RecentConViewHolder(holder.itemView).setData(msglist[position])
    }

    override fun getItemCount(): Int {
        return msglist.size
    }
}
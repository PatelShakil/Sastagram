package com.genie.main_module

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.genie.R
import com.genie.databinding.SampleMainAppsBinding
import com.genie.social_media.SocialMediaActivity

class adapter_main_apps: RecyclerView.Adapter<adapter_main_apps.MainAppsViewHolder> {
    var context:Context
    var apps:ArrayList<String>

    constructor(context: Context, apps: ArrayList<String>) : super() {
        this.context = context
        this.apps = apps
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAppsViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.sample_main_apps,null,false)
        return MainAppsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAppsViewHolder, position: Int) {
        var app = apps[position]
        holder.binding.mainAppsBtn.text = app
        holder.binding.mainAppsBtn.setOnClickListener {
            if(holder.binding.mainAppsBtn.text == "Social Media"){
                context.startActivity(Intent(context,SocialMediaActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int {
        return apps.size
    }
    class MainAppsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var binding:SampleMainAppsBinding = SampleMainAppsBinding.bind(itemView)
    }
}
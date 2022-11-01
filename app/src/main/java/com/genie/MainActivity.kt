package com.genie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.genie.databinding.ActivityMainBinding
import com.genie.main_module.adapter_main_apps

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var apps = ArrayList<String>()
        var pinnedapps = ArrayList<String>()
        pinnedapps.add("Social Media")
        pinnedapps.add("Video Platform")
        pinnedapps.add("E-Commerce")
        pinnedapps.add("Gaming")
        apps.add("Editing Platform")
        apps.add("Music")
        apps.add("Dating")
        apps.add("Bookings")
        apps.add("Hiring")
        apps.add("Ed. Tech")
        apps.add("Health & Fitness")
        apps.add("Beauty")
        apps.add("News")
        apps.add("Memes")
        apps.add("Freelance")
        apps.add("PDF & Docs")
        apps.add("Trading")
        apps.add("Comedy")
        apps.add("Legal & Consult")
        apps.add("Sharing")
        var mainappsadapter = adapter_main_apps(this,apps)
        var pinnedappsadapter = adapter_main_apps(this,pinnedapps)
        binding.mainAppsRv.adapter = mainappsadapter
        binding.pinnedAppsRv.adapter = pinnedappsadapter
    }
}
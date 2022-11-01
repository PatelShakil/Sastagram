package com.genie.social_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.Toast
import com.genie.R
import com.genie.databinding.ActivitySocialMediaBinding
import com.genie.social_media.fragments.*

class SocialMediaActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySocialMediaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navigation.selectedItemId = R.id.social_media_home
        supportFragmentManager.beginTransaction().replace(R.id.main_container,HomeFragment()).commit()
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.social_media_live -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, LiveStreamFragment()).commit()
                }
                R.id.social_media_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, SearchFragment()).commit()
                }
                R.id.social_media_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, HomeFragment()).commit()
                }
                R.id.social_media_chats -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ChatsFragment()).commit()
                }
                R.id.social_media_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ProfileFragment()).commit()
                }
            }
            true
        }
    }
}
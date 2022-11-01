package com.genie.social_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        supportFragmentManager.beginTransaction().replace(R.id.main_container,HomeFragment())
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.social_media_live -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, LiveStreamFragment())
                    Toast.makeText(this,"live",Toast.LENGTH_SHORT).show()
                }
                R.id.social_media_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, SearchFragment())
                }
                R.id.social_media_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, HomeFragment())
                }
                R.id.social_media_chats -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ChatsFragment())
                }
                R.id.social_media_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ProfileFragment())
                }
            }
            true
        }
    }
}
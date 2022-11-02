package com.genie.social_media

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.view.ViewGroup
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
//        binding.navigationNv.add(MeowBottomNavigation.Model(1,R.drawable.live))
//        binding.navigationNv.add(MeowBottomNavigation.Model(2,R.drawable.search_icon))
//        binding.navigationNv.add(MeowBottomNavigation.Model(3,R.drawable.home_icon))
//        binding.navigationNv.add(MeowBottomNavigation.Model(4,R.drawable.chats_icon))
//        binding.navigationNv.add(MeowBottomNavigation.Model(5,R.drawable.person_icon))
//
//        binding.navigationNv.setOnClickMenuListener {
//            when (it.id){
//                1 -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_container, LiveStreamFragment()).commit()
//                }
//                2 -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_container, SearchFragment()).commit()
//                }
//                3 -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_container, HomeFragment()).commit()
//                }
//                4 -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_container, ChatsFragment()).commit()
//                }
//                5 -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_container, ProfileFragment()).commit()
//                }
//            }
//        }
//        binding.navigationNv.show(3,true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
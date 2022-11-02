package com.genie.social_media

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        supportFragmentManager.beginTransaction().replace(R.id.main_container,HomeFragment(),"home").addToBackStack("home").commit()
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.social_media_live -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, LiveStreamFragment(),"live").addToBackStack("live").commit()
                }
                R.id.social_media_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, SearchFragment(),"search").addToBackStack("search").commit()
                }
                R.id.social_media_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, HomeFragment(),"home").addToBackStack("home").commit()
                }
                R.id.social_media_chats -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ChatsFragment(),"chats").addToBackStack("chats").commit()
                }
                R.id.social_media_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ProfileFragment(),"profile").addToBackStack("profile").commit()
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

//        if (supportFragmentManager.findFragmentByTag("home")?.isVisible!!) {
//            binding.navigation.selectedItemId = R.id.social_media_home
//        }
//        else if (supportFragmentManager.findFragmentByTag("chats")?.isVisible!!){
//            binding.navigation.selectedItemId = R.id.social_media_chats
//        }
//        else if (supportFragmentManager.findFragmentByTag("profile")?.isVisible!!){
//            binding.navigation.selectedItemId = R.id.social_media_profile
//        }
//        else if (supportFragmentManager.findFragmentByTag("live")?.isVisible!!){
//            binding.navigation.selectedItemId = R.id.social_media_live
//        }
//        else if (supportFragmentManager.findFragmentByTag("search")?.isVisible!!){
//            binding.navigation.selectedItemId = R.id.social_media_search
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed();
        }    }
}
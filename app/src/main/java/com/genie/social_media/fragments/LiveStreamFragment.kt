package com.genie.social_media.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.genie.R
import com.genie.databinding.ActivitySocialMediaBinding
import com.genie.databinding.FragmentLiveStreamBinding

class LiveStreamFragment : Fragment() {
    lateinit var binding: FragmentLiveStreamBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLiveStreamBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        Toast.makeText(context,"live stream",Toast.LENGTH_SHORT).show()
        return binding.root
    }
}
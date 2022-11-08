package com.instagram.social_media.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.instagram.databinding.FragmentLiveStreamBinding

class LiveStreamFragment : Fragment() {
    lateinit var binding: FragmentLiveStreamBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLiveStreamBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }
}
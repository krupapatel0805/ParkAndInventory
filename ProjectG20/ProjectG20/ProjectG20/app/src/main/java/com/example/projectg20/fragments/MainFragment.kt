package com.example.projectg20.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projectg20.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up bottom navigation
        val bottomNav: BottomNavigationView = view.findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_find_park -> {
                    // Log message when "Find Park" option is clicked
                    Log.d(TAG, "Navigating to FindParkFragment")
                    findNavController().navigate(R.id.action_mainFragment_to_findParkFragment)
                    true
                }
                R.id.action_itinerary -> {
                    // Log message when "My Itinerary" option is clicked
                    Log.d(TAG, "Navigating to MyItineraryFragment")

                    findNavController().navigate(R.id.action_mainFragment_to_myItineraryFragment)
                    true
                }
                else -> false
            }
        }
    }
}

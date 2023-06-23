package com.example.projectg20.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.example.projectg20.NationalParkData
import com.example.projectg20.ParkData
import com.example.projectg20.R
import com.example.projectg20.databinding.FragmentParkDetailsBinding

class ParkDetailsFragment : Fragment() {
    private val TAG: String = "ParkDetailsFragment: "

    private var _binding: FragmentParkDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var stateCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stateCode = it.getString("stateCode") ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParkDetailsBinding.inflate(inflater, container, false)
        Log.d(TAG, "1. Details Fragment Created")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Filter the list of national parks by state code
        val parksInState = ParkData.nationalParks.filter { park -> park.stateCode == stateCode }

        // Display the details of the national parks in the selected state
        displayParksDetails(parksInState)
    }

    private fun displayParksDetails(parksInState: List<NationalParkData>) {
        for (park in parksInState) {
            if (park.stateCode == "AK") {
                binding.parkName.text = park.fullName
                binding.TVAddress.text = park.address
                binding.TVWebsite.text = park.url
                binding.TVDescription.text = park.description
            } else {
                binding.parkName.text = "Acadia National Park"
                binding.TVAddress.text = "PO Box 177, Bar Harbor, ME 04609, USA"
                binding.TVWebsite.text = "https://www.nps.gov/acad/index.htm"
                binding.TVDescription.text = "Acadia National Park protects the natural beauty of the highest rocky headlands along the Atlantic coastline of the United States, an abundance of habitats with high biodiversity, clean air and water, and a rich cultural heritage. Each year, more than 3.5 million people explore seven peaks above 1,000 feet, 158 miles of hiking trails, and 45 miles of carriage roads with 16 stone bridges."
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.projectg20.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.projectg20.NationalParkData
import com.example.projectg20.ParkData
import com.example.projectg20.R
import com.example.projectg20.databinding.FragmentFindParkBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FindParkFragment : Fragment(), OnMapReadyCallback {

    private val TAG:String = "FindParkFragment: "

    private var _binding: FragmentFindParkBinding ?= null
    private val binding get() = _binding!!

    private lateinit var gMap: GoogleMap

    private lateinit var parksInState: List<NationalParkData>

    private var selectedStateCode: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindParkBinding.inflate(inflater, container, false)
        Log.d(TAG, "1. Fragment Created")
        return binding.root
    }

    private suspend fun getParksInState(stateCode: String): List<NationalParkData> {
        return withContext(Dispatchers.IO) {
            ParkData.nationalParks.filter { park -> park.stateCode == stateCode }
        }
    }

    private fun showParksOnMap(parksInState: List<NationalParkData>) {

        // Add a marker to the map for each park in the selected state
        for (park in parksInState) {
            val position = LatLng(park.latitude, park.longitude)
            val markerOptions = MarkerOptions()
                .position(position)
                .title(park.fullName)

            gMap.addMarker(markerOptions)
        }
    }

    private suspend fun displayParksOnMap(parks: List<NationalParkData>) {
        withContext(Dispatchers.Main) {
            showParksOnMap(parks)
        }
    }

    private fun onStateSelected(code: String) {
        lifecycleScope.launch {
            val parks = getParksInState(code)
            displayParksOnMap(parks)
        }
    }

    private fun viewParkDetails(stateCode: String) {
        // Navigate to ParkDetailsFragment using SafeArgs to pass the state code
        val action = FindParkFragmentDirections.actionFindParkFragmentToParkDetailsFragment()
        findNavController().navigate(action)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get a reference to the map fragment
        val mapFragment =  childFragmentManager.findFragmentById(binding.mapView.id) as? SupportMapFragment

        // debugging messages to help determine if the screen can find the map
        if (mapFragment == null) {
            Log.d(TAG, "++++ map fragment is null")
        } else {
            Log.d(TAG, "++++ map fragment is NOT null")
            mapFragment?.getMapAsync(this)
        }

        // Set up state spinner
        val stateAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.states_array,
            android.R.layout.simple_spinner_item
        )

        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.stateSpinner.prompt = "Select state"
        binding.stateSpinner.adapter = stateAdapter

        var statesArray = resources.getStringArray(R.array.states_array)

        // Set up a listener for when the state spinner value changes
        binding.stateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                // Get the selected state name from the spinner
                val stateName = parent.getItemAtPosition(position).toString()
                Log.d(TAG, "Selected State: $stateName")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Set up Find Parks button
        binding.findParksBtn.setOnClickListener {

            // Retrieve a list of parks in the selected state and display them on the map
            val selectedState = binding.stateSpinner.selectedItem.toString()
            val parts = selectedState.split(" - ")
            if (parts.size == 2) {
                val stateCode = parts[0]
                selectedStateCode = stateCode

                lifecycleScope.launch {
                    val parks = ParkData.nationalParks.filter { park -> park.stateCode.contains(stateCode) }

                    // Show markers for each park on the map if stateCode exists
                    if (parks.isNotEmpty()) {
                        // Clear existing markers from the map
                        gMap.clear()

                        // Show markers for each park on the map
                        showParksOnMap(parks)

                        // Set up View Details button
                        binding.parksDetailsBtn.setOnClickListener {
                            viewParkDetails(stateCode)
                        }

                    } else {
                        Toast.makeText(context, "No national parks found in $selectedState", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: GoogleMap) {
        gMap = map
        gMap.uiSettings.isZoomControlsEnabled = true
        Log.d(TAG, "2. Map Ready")

        // configure the map's options
        // - set the map type (hybrid, satellite, etc)
        gMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        // - select if traffic data should be displayed
        gMap.isTrafficEnabled = true
        // - add user interface controls to the map (zoom, compass, etc)
        val uiSettings = map.uiSettings
        uiSettings.isZoomControlsEnabled = true
        uiSettings.isCompassEnabled = true


        // move the map to the initial location you want the user to see
        // - zoom out: smaller number (1,2,3...)
        // - zoom in: larger number  (10,11,12...)
        val intialLocation = LatLng(37.0902, -95.7129)
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(intialLocation, 2.0f))

        // add a USA marker to the map
        gMap.addMarker(MarkerOptions().position(intialLocation).title("USA"))

    }

}

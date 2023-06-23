package com.example.projectg20.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_g20.models.Park
import com.example.projectg20.databinding.FragmentMyItineraryDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class My_itinerary_details : Fragment() {

    private lateinit var binding: FragmentMyItineraryDetailsBinding
    private lateinit var park: Park
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var itineraryId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using view binding
        binding = FragmentMyItineraryDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        // Get the data passed from the previous fragment
        park = arguments?.getSerializable("park") as? Park ?: Park()

        // Set the park details to the corresponding views
        binding.parkName.text = park.name
        binding.parkDistance.text = park.distance.toString()

        // Set the park date to the corresponding view
        binding.parkDate.text = park.date.toString()

        // Set up the notes edit text
        binding.parkNotesEditText.setText(park.notes)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Create a unique itinerary ID based on the park name
        itineraryId = park.name

        // Set up the update button
        binding.updateDateButton.setOnClickListener {
            updateDate()
        }

        // Set up the save notes button
        binding.saveButton.setOnClickListener {
            saveNotes()
        }

        return view
    }

    // Function to update the date in the database
    private fun updateDate() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("parks/$itineraryId/date")

        val newDate = binding.parkDate.text.toString()

        databaseRef.setValue(newDate).addOnSuccessListener {
            // Update the park object with the new date
            park.date = newDate
        }
    }

    // Function to save the notes in the database
    private fun saveNotes() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("parks/$itineraryId/notes")

        val notes = binding.parkNotesEditText.text.toString()

        databaseRef.setValue(notes).addOnSuccessListener {
            // Update the park object with the new notes
            park.notes = notes
        }
    }
}
package com.example.projectg20.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_g20.models.Park
import com.example.projectg20.R
import com.example.projectg20.databinding.FragmentMyItineraryListBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class My_itinerary_list : Fragment() {

    private lateinit var binding: FragmentMyItineraryListBinding
    private lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: FirebaseRecyclerAdapter<Park, ParkViewHolder>
    private lateinit var itinerary_recyclerview: RecyclerView
    private lateinit var removeParkButton: Button
    private lateinit var itineraryId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using view binding
        binding = FragmentMyItineraryListBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set up the RecyclerView
        itinerary_recyclerview = binding.itineraryRecyclerview
        layoutManager = LinearLayoutManager(context)
        itinerary_recyclerview.layoutManager = layoutManager

        // Set up the FirebaseRecyclerAdapter
        val query = FirebaseDatabase.getInstance().getReference("itineraries")
        val options = FirebaseRecyclerOptions.Builder<Park>()
            .setQuery(query, Park::class.java)
            .build()

        adapter = object : FirebaseRecyclerAdapter<Park, ParkViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.park_item_list, parent, false)
                return ParkViewHolder(view)
            }

            override fun onBindViewHolder(holder: ParkViewHolder, position: Int, model: Park) {
                holder.bind(model)
            }
        }

        // Set the adapter to the RecyclerView
        itinerary_recyclerview.adapter = adapter

        // Set up the button for removing itinerary from the list
//        holder.removeParkButton.setOnClickListener {
//            val databaseRef = FirebaseDatabase.getInstance().getReference("itineraries").child(itineraryId).child("parks").child(adapter.getRef(holder.adapterPosition).key!!)
//            databaseRef.removeValue().addOnSuccessListener {
//                // Remove the park from the local itinerary list
//                (activity as MainActivity).removeParkFromItinerary(adapter.getItem(holder.adapterPosition))
//                // Navigate back to the My Itinerary fragment
//                (activity as MainActivity).navigateToMyItineraryFragment()
//            }
//        }
        return view
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    inner class ParkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val parkNameTextView: TextView = itemView.findViewById(R.id.park_name)
        private val parkDateTextView: TextView = itemView.findViewById(R.id.park_date)
        private val parkDistanceTextView: TextView = itemView.findViewById(R.id.park_distance)

        private lateinit var park: Park

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(park: Park) {
            // Set the park details to the corresponding views
            parkNameTextView.text = park.name
            parkDateTextView.text = park.date
            parkDistanceTextView.text = park.distance.toString()

            // Save the park details for the click listener
            this.park = park
        }

        override fun onClick(view: View?) {
            Log.d("My_itinerary_list", "Item clicked")

            // Launch the My_itinerary_details fragment with the clicked park details
            val fragment = My_itinerary_details()
            val bundle = Bundle()
            bundle.putString("itineraryId", adapter.getRef(adapterPosition).key)
            bundle.putSerializable("park", park)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container1, fragment)
                .addToBackStack(null)
                .commit()
        }

    }
}
package com.example.testoviy_dating.bottomfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.R
import com.example.testoviy_dating.databinding.FragmentSearchBinding
import com.example.testoviy_dating.newadapters.SearchBoysAdapter
import com.example.testoviy_dating.newreg.BoysReg
import com.example.testoviy_dating.newreg.GirlsReg
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchBoysAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    private var list2: ArrayList<GirlsReg> = ArrayList()
    private val TAG = "SearchFragment"
    private var selectedHeight: String? = "All heights"
    private var selectedWeight: String? = "All weights"
    private var selectedRegion: String? = "All regions"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        firebaseFirestore = FirebaseFirestore.getInstance()

        val gender = (activity as? BottomActivity)?.intent?.getStringExtra("gender")

        if (gender == "Male") {
            Toast.makeText(binding.root.context, "You are male", Toast.LENGTH_SHORT).show()
            setSpinner()
            setWeightSpinner()
            setRegions()
        } else {
            Toast.makeText(binding.root.context, "You are female", Toast.LENGTH_SHORT).show()
        }

        // Initialize adapter and set it to RecyclerView
        adapter = SearchBoysAdapter(list2, object : SearchBoysAdapter.OnItremClickListener {
            override fun onItemClick(malumotlar: GirlsReg) {
                // Handle item click
            }
        })
        binding.rv.adapter = adapter

        // Load data initially for all filters
        loadData()

        return binding.root
    }

    private fun setRegions() {
        val regionSpinner = binding.regions
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.sortRegions,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            regionSpinner.adapter = adapter
        }

        regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedRegion = if (position == 0) "All regions" else parent.getItemAtPosition(position).toString()
                loadData()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedRegion = "All regions"
            }
        }
    }

    private fun setWeightSpinner() {
        val weightSpinner = binding.weightSpinner
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.weight_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            weightSpinner.adapter = adapter
        }

        weightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedWeight = parent.getItemAtPosition(position).toString()
                loadData()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setSpinner() {
        val heightSpinner = binding.heightSpinner
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.height_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            heightSpinner.adapter = adapter
        }

        heightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedHeight = parent.getItemAtPosition(position).toString()
                loadData()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun loadData() {
        Log.d(TAG, "Loading data for height: $selectedHeight, weight: $selectedWeight, and region: $selectedRegion")

        firebaseFirestore.collection("girl_reg")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    list2.clear()  // Clear the existing list before adding new data
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->
                        val girlReg = queryDocumentSnapshot.toObject(GirlsReg::class.java)
                        val heightMatch = when (selectedHeight) {
                            "All heights" -> true
                            "1.50 to 1.60" -> girlReg.GirlsResponse!!.Fourth == "A"
                            "1.60 to 1.75" -> girlReg.GirlsResponse!!.Fourth == "B"
                            "1.75+" -> girlReg.GirlsResponse!!.Fourth == "C"
                            else -> false
                        }
                        val weightMatch = when (selectedWeight) {
                            "All weights" -> true
                            "45 kg to 55" -> girlReg.GirlsResponse!!.Fifth == "A"
                            "55 to 65" -> girlReg.GirlsResponse!!.Fifth == "B"
                            "65+" -> girlReg.GirlsResponse!!.Fifth == "C"
                            else -> false
                        }
                        val regionMatch = selectedRegion == "All regions" || girlReg.Region == selectedRegion

                        if (heightMatch && weightMatch && regionMatch) {
                            list2.add(girlReg)
                        }
                    }
                    adapter.notifyDataSetChanged()  // Notify the adapter of the data changes
                } else {
                    Log.e(TAG, "Error fetching data", task.exception)
                }
            }
    }











    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
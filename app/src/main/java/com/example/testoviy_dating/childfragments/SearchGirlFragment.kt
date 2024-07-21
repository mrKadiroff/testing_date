package com.example.testoviy_dating.childfragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.R
import com.example.testoviy_dating.databinding.FragmentGirlsResponseBinding
import com.example.testoviy_dating.databinding.FragmentSearchGirlBinding
import com.example.testoviy_dating.newadapters.SearchBoysAdapter
import com.example.testoviy_dating.newadapters.SearchGirlsAdapter
import com.example.testoviy_dating.newreg.BoysReg
import com.example.testoviy_dating.newreg.GirlsReg
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchGirlFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchGirlFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentSearchGirlBinding
    private var selectedSalary: String? = "All salaries"
    private var selectedHeight: String? = "All heights"
    private var selectedRegion: String? = "All regions"
    private var selectedAge: String? = "All ages"
    private lateinit var adapter: SearchGirlsAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    private var list2: ArrayList<BoysReg> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchGirlBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()
        setSalarySpinner()
        setHeightPinner()
        setRegions()
        setAgeSpinner()


        // Initialize adapter and set it to RecyclerView
        adapter = SearchGirlsAdapter(list2, object : SearchGirlsAdapter.OnItremClickListener {
            override fun onItemClick(malumotlar: BoysReg) {

            }

        })
        binding.rv.adapter = adapter

        // Load data initially for all filters
        loadData()

        return binding.root
    }

    private fun setAgeSpinner() {
        val ageSpinner = binding.age
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.age_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            ageSpinner.adapter = adapter
        }

        ageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedAge = if (position == 0) "All ages" else parent.getItemAtPosition(position).toString()
                loadData()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedAge = "All ages"
            }
        }
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





    private fun setHeightPinner() {
        val salarySpinner = binding.heightSpinner
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.height_optionsboys,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            salarySpinner.adapter = adapter
        }

        salarySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedHeight = if (position == 0) "All heights" else parent.getItemAtPosition(position).toString()
                loadData()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedHeight = "All heights"

            }
        }
    }
    private fun parseAge(ageString: String?): Int? {
        return ageString?.toIntOrNull()
    }
    private fun loadData() {


        firebaseFirestore.collection("boy_reg")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    list2.clear()  // Clear the existing list before adding new data
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->
                        val boyReg = queryDocumentSnapshot.toObject(BoysReg::class.java)

                        val heightMatch = when (selectedHeight) {
                            "All heights" -> true
                            "1.50 to 1.60" -> boyReg.BoysResponse!!.Third == "A"
                            "1.60 to 1.75" -> boyReg.BoysResponse!!.Third == "B"
                            "1.75+" -> boyReg.BoysResponse!!.Third == "C"
                            else -> false
                        }

                        val salaryMatch = when (selectedSalary) {
                            "All salaries" -> true
                            ". 2-4 million so'm" -> boyReg.BoysResponse!!.First == "A"
                            "5-9 million so'm." -> boyReg.BoysResponse!!.First == "B"
                            "10mln +." -> boyReg.BoysResponse!!.First == "C"
                            else -> false
                        }
                        val regionMatch = selectedRegion == "All regions" || boyReg.Region == selectedRegion


                        val ageMatch = when (selectedAge) {
                            "All ages" -> true
                            "Under 18" -> parseAge(boyReg.Age)?.let { it < 18 } == true
                            "18-24" -> parseAge(boyReg!!.Age)?.let { it in 18..24 } == true
                            "25-34" -> parseAge(boyReg!!.Age)?.let { it in 25..34 } == true
                            "35-44" -> parseAge(boyReg!!.Age)?.let { it in 35..44 } == true
                            "45-54" -> parseAge(boyReg!!.Age)?.let { it in 45..54 } == true
                            "55-64" -> parseAge(boyReg!!.Age)?.let { it in 55..64 } == true
                            else -> false
                        }

                        if (salaryMatch&&heightMatch&&regionMatch&&ageMatch) {
                            list2.add(boyReg)
                        }
                    }
                    adapter.notifyDataSetChanged()  // Notify the adapter of the data changes
                } else {
                    Log.e(TAG, "Error fetching data", task.exception)
                }
            }
    }

    private fun setSalarySpinner() {
        val salarySpinner = binding.salarySpinner
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.salary_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            salarySpinner.adapter = adapter
        }

        salarySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedSalary = if (position == 0) "All salaries" else parent.getItemAtPosition(position).toString()
                loadData()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedSalary = "All salaries"

            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as BottomActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as BottomActivity).showBottomNavigation()
        super.onDetach()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchGirlFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchGirlFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
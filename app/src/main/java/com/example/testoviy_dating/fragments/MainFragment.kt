package com.example.testoviy_dating.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testoviy_dating.R
import com.example.testoviy_dating.databinding.FragmentMainBinding
import com.example.testoviy_dating.models.Registration
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
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

    lateinit var binding: FragmentMainBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()

        setSpinner()
        addToFirestore()


        binding.next.setOnClickListener {
            findNavController().navigate(R.id.dashboardFragment)
        }


        return binding.root
    }

    private fun addToFirestore() {

//        binding.add.setOnClickListener {
//            // Set the item selected listener
//            val name = binding.name.text.toString().trim()
//            val surname = binding.surname.text.toString().trim()
//            val age = binding.age.text.toString().trim()
//            val gender = binding.genderSpinner.selectedItem.toString()
//            val password = binding.password.text.toString().trim()
//            val passwordRecovery = binding.passwordrecovery.text.toString().trim()
//
//            if (name.isNotEmpty() && surname.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty() && password.isNotEmpty() && passwordRecovery.isNotEmpty()) {
//                val registration =
//                    Registration(name, surname, age, gender, password, passwordRecovery)
//
//
//                firebaseFirestore.collection("registration")
//                    .add(registration)
//                    .addOnSuccessListener {
//                        Toast.makeText(
//                            binding.root.context,
//                            "Succesfully saved",
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
//                        requireFragmentManager().popBackStack()
//
//
//                    }.addOnFailureListener {
//                        Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
//                    }
//            } else {
//                Toast.makeText(
//                    binding.root.context,
//                    "Please add full information",
//                    Toast.LENGTH_SHORT
//                ).show()
//
//            }
//
//
//        }
    }

    private fun setSpinner() {
        val genderSpinner = binding.genderSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.gender_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            genderSpinner.adapter = adapter
        }





    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
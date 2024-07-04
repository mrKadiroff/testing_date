package com.example.testoviy_dating.bottomfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.R
import com.example.testoviy_dating.databinding.FragmentDashboardBinding
import com.example.testoviy_dating.databinding.FragmentSearchBinding
import com.example.testoviy_dating.newadapters.BoyReg
import com.example.testoviy_dating.newadapters.UsersAdapter
import com.example.testoviy_dating.newadapters.UsersGirlsAdapter
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

    lateinit var binding: FragmentSearchBinding
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: UsersAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<BoyReg>
    lateinit var list2: ArrayList<GirlsReg>
    private  val TAG = "SearchFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()


//     fetchDataFromFirestore()
        fetchDataforBoys()


        return binding.root
    }

    private fun fetchDataforBoys() {
        var usersGirlsAdapter:UsersGirlsAdapter
        list2 = ArrayList()
        firebaseFirestore.collection("girl_reg")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->
                        // Log the raw document data
                        Log.d("FirestoreData", "Document data: ${queryDocumentSnapshot.data}")

                        // Convert document to BoysReg object
                        val girlReg = queryDocumentSnapshot.toObject(GirlsReg::class.java)

                        Log.d("FirestoreData", "GirlsExpectation: ${girlReg.GirlsExpectation}")
                        Log.d("FirestoreData", "GIrlsResponse: ${girlReg.GirlsResponse}")


                        list2.add(girlReg)
                        usersGirlsAdapter = UsersGirlsAdapter(list2,object :UsersGirlsAdapter.OnItremClickListener{
                            override fun onItemClick(malumotlar: GirlsReg) {

                            }


                        })

                        binding.rv.adapter = usersGirlsAdapter
                    }
                } else {
                    Log.e("FirestoreError", "Error getting documents.", task.exception)
                    Toast.makeText(binding.root.context, "Error getting documents.", Toast.LENGTH_SHORT).show()
                }
            }

        binding.check.setOnClickListener {
            if (list.isNotEmpty()) {
                Toast.makeText(binding.root.context, list[0].BoysQuest?.First, Toast.LENGTH_SHORT).show()
            }
        }



    }




//    private fun fetchDataFromFirestore() {
//        list = ArrayList()
//        firebaseFirestore.collection("boy_reg")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val result = task.result
//                    result?.forEach { queryDocumentSnapshot ->
//                        // Log the raw document data
//                        Log.d("FirestoreData", "Document data: ${queryDocumentSnapshot.data}")
//
//                        // Convert document to BoysReg object
//                        val boyReg = queryDocumentSnapshot.toObject(BoyReg::class.java)
//                        Log.d("FirestoreData", "Converted data: $boyReg")
//
//                        // Check and log BoysExpectation and BoysResponse fields
//                        Log.d("FirestoreData", "BoysExpectation: ${boyReg.BoysQuest}")
//                        Log.d("FirestoreData", "BoysResponse: ${boyReg.BoysResponse}")
//
//                        list.add(boyReg)
//                        adapter = UsersAdapter(list,object :UsersAdapter.OnItremClickListener{
//                            override fun onItemClick(malumotlar: BoyReg) {
//
//                            }
//
//                        })
//
//                        binding.rv.adapter = adapter
//                    }
//                } else {
//                    Log.e("FirestoreError", "Error getting documents.", task.exception)
//                    Toast.makeText(binding.root.context, "Error getting documents.", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        binding.check.setOnClickListener {
//            if (list.isNotEmpty()) {
//                Toast.makeText(binding.root.context, list[0].BoysQuest?.First, Toast.LENGTH_SHORT).show()
//            }
//        }
//    }




//    private fun setRv() {
//        list = ArrayList()
//        firebaseFirestore.collection("boy_reg")
//            .get()
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    val result = it.result
//
//                    result?.forEach { queryDocumentSnapshot ->
//                        val boyReg = queryDocumentSnapshot.toObject(
//                            BoyReg::class.java)
//
//                        binding.check.setOnClickListener {
//                            Toast.makeText(binding.root.context, boyReg.BoysQuest!!.First, Toast.LENGTH_SHORT).show()
//                        }
//
//                        list.add(boyReg)
//
//                        adapter = UsersAdapter(list,object :
//                            UsersAdapter.OnItremClickListener{
//                            override fun onItemClick(malumotlar: BoyReg) {
//                            }
//
//
//                        })
//                        binding.rv.adapter = adapter
//
//
//                    }
//                }}
//    }

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
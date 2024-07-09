package com.example.testoviy_dating.bottomfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.R
import com.example.testoviy_dating.databinding.FragmentRecommendationBinding
import com.example.testoviy_dating.newadapters.UsersBoysAdapter
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
 * Use the [RecommendationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecommendationFragment : Fragment() {
    lateinit var binding: FragmentRecommendationBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<GirlsReg>
    lateinit var list2: ArrayList<BoysReg>
    private val TAG = "RecommendationFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecommendationBinding.inflate(layoutInflater, container, false)
        firebaseFirestore = FirebaseFirestore.getInstance()

        val gender = (activity as? BottomActivity)?.intent?.getStringExtra("gender")

        if (gender == "Male") {
            setEmptyRv()
        } else {
            Toast.makeText(binding.root.context, "You are female", Toast.LENGTH_SHORT).show()
            setRvForGirls()
        }

        return binding.root
    }

    private fun setRvForGirls() {
        // Implement if needed
        var adapter : UsersBoysAdapter
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
                        val passowrd = (activity as? BottomActivity)?.intent?.getStringExtra("password")
                        val girlReg = queryDocumentSnapshot.toObject(GirlsReg::class.java)

                        if (girlReg.Password == passowrd){
                            fetchDataFromFirebaseforGirls { boysList ->
                                val usersBoysAdapter = UsersBoysAdapter(boysList, girlReg, object : UsersBoysAdapter.OnItremClickListener {
                                    override fun onItemClick(
                                        malumotlar: BoysReg,
                                        percentage: Int,
                                        girlReg: GirlsReg
                                    ) {
                                        var bundle = Bundle()
                                        bundle.putInt("percentage",percentage)
                                        bundle.putSerializable("userDataGirl",girlReg)
                                        bundle.putSerializable("boyData",malumotlar)
                                        findNavController().navigate(R.id.girlsPercentageFragment,bundle)
                                    }


                                })

                              binding.rv.adapter = usersBoysAdapter
                            }
                        }


                    }
                } else {
                    Log.e("FirestoreError", "Error getting documents.", task.exception)
                    Toast.makeText(binding.root.context, "Error getting documents.", Toast.LENGTH_SHORT).show()
                }
            }





    }





    private fun setEmptyRv() {
        list = ArrayList()
        firebaseFirestore.collection("boy_reg")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->
                        // Convert document to BoysReg object
                        val passowrd = (activity as? BottomActivity)?.intent?.getStringExtra("password")
                        val boyReg = queryDocumentSnapshot.toObject(BoysReg::class.java)

                        if (boyReg.Password == passowrd){
                            fetchDataFromFirebase { girlsList ->
                                val usersGirlsAdapter = UsersGirlsAdapter(girlsList, boyReg, object : UsersGirlsAdapter.OnItremClickListener {
                                    override fun onItemClick(
                                        malumotlar: GirlsReg,
                                        percentage: Int,
                                        boyReg: BoysReg
                                    ) {
                                        var bundle = Bundle()
                                        bundle.putString("gender","Male")
                                        bundle.putInt("percentage",percentage)
                                        bundle.putSerializable("userDataBoy",boyReg)
                                        bundle.putSerializable("girlData",malumotlar)
                                        findNavController().navigate(R.id.percentageFragment,bundle)
                                    }

                                })

                                binding.rv.adapter = usersGirlsAdapter
                            }
                        }



                    }
                }
            }
    }


    private fun fetchDataFromFirebase(callback: (List<GirlsReg>) -> Unit) {
        val girlsList = ArrayList<GirlsReg>()

        firebaseFirestore.collection("girl_reg")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->
                        val girlReg = queryDocumentSnapshot.toObject(GirlsReg::class.java)
                        girlsList.add(girlReg)
                    }
                    callback(girlsList) // Call the callback after fetching all data
                }
            }
    }



    private fun fetchDataFromFirebaseforGirls(callback: (List<BoysReg>) -> Unit) {
        val boysList = ArrayList<BoysReg>()

        firebaseFirestore.collection("boy_reg")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->
                        val girlReg = queryDocumentSnapshot.toObject(BoysReg::class.java)
                        boysList.add(girlReg)
                    }
                    callback(boysList) // Call the callback after fetching all data
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
         * @return A new instance of fragment RecommendationFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecommendationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
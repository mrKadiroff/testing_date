package com.example.testoviy_dating.bottomfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.databinding.FragmentRecommendationBinding
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

    lateinit var binding: FragmentRecommendationBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<GirlsReg>
    private val TAG = "RecommendationFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecommendationBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()


        val gender = (activity as? BottomActivity)?.intent?.getStringExtra("gender")

        if (gender == "Male"){
            setRv()
            boyData()
        }else{
            Toast.makeText(binding.root.context, "You are fucking female", Toast.LENGTH_SHORT).show()
            setRvForGIrls()
        }
        
        
       
       


        return binding.root
    }

    private fun setRvForGIrls() {

    }

    private fun setRv() {
        var usersGirlsAdapter: UsersGirlsAdapter
        list = ArrayList()
        firebaseFirestore.collection("girl_reg")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->

                        val girlReg = queryDocumentSnapshot.toObject(GirlsReg::class.java)
                        list.add(girlReg)
                        boyData()


                        firebaseFirestore.collection("boy_reg")
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val result = task.result
                                    result?.forEach { queryDocumentSnapshot ->

                                        // Convert document to BoysReg object
                                        val boyReg = queryDocumentSnapshot.toObject(BoysReg::class.java)



                                        usersGirlsAdapter = UsersGirlsAdapter(list,boyReg,object : UsersGirlsAdapter.OnItremClickListener{
                                            override fun onItemClick(malumotlar: GirlsReg) {

                                            }


                                        })

                                        binding.rv.adapter = usersGirlsAdapter




                                    }
                                }
                            }









                    }
                } else {
                    Log.e("FirestoreError", "Error getting documents.", task.exception)
                    Toast.makeText(binding.root.context, "Error getting documents.", Toast.LENGTH_SHORT).show()
                }
            }



    }

    private fun boyData() {
        firebaseFirestore.collection("boy_reg")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->

                        // Convert document to BoysReg object
                        val girlReg = queryDocumentSnapshot.toObject(BoysReg::class.java)
                        Log.d(TAG, "boyData: ${girlReg.BoysExpectation!!.First} va ${girlReg.BoysResponse!!.First}")

                    }
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
        // TODO: Rename and change types and number of parameters
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
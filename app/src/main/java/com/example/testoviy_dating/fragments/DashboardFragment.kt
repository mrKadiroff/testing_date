package com.example.testoviy_dating.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoviy_dating.R
import com.example.testoviy_dating.adapter.RegistrationAdapter
import com.example.testoviy_dating.bottomfragments.AboutFragment
import com.example.testoviy_dating.bottomfragments.AccountFragment
import com.example.testoviy_dating.bottomfragments.RecommendationFragment
import com.example.testoviy_dating.bottomfragments.SearchFragment
import com.example.testoviy_dating.databinding.FragmentDashboardBinding
import com.example.testoviy_dating.models.Registration
import com.google.firebase.firestore.FirebaseFirestore


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
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

    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var binding: FragmentDashboardBinding
    lateinit var list: ArrayList<Registration>
    lateinit var adapter: RegistrationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()


//        readcha()




        return binding.root

    }


//
//    private fun readcha() {
//        list = ArrayList()
//        firebaseFirestore.collection("registration")
//            .get()
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    val result = it.result
//
//                    result?.forEach { queryDocumentSnapshot ->
//                        val registration = queryDocumentSnapshot.toObject(Registration::class.java)
//                        list.add(registration)
//
//
//                        adapter = RegistrationAdapter(list)
//                        binding.rv.adapter = adapter
//
//
//
//                    }
//                }}
//
//
//
//
//    }






    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
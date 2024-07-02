package com.example.testoviy_dating.bottomfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.adapter.RegistrationAdapter
import com.example.testoviy_dating.databinding.FragmentRecommendationBinding
import com.example.testoviy_dating.models.GirlsResponse
import com.example.testoviy_dating.models.Registration
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
    lateinit var adapter: RegistrationAdapter
    lateinit var list: ArrayList<Registration>
    lateinit var list2: ArrayList<GirlsResponse>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecommendationBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()

        setRv()
        binding.sort.setOnClickListener {
            adapter.sortAndNotify()
        }


        return binding.root
    }

    private fun setRv() {
        list = ArrayList()
        list2 = ArrayList()
        val password = (activity as? BottomActivity)?.intent?.getStringExtra("password")
        val gender = (activity as? BottomActivity)?.intent?.getStringExtra("gender")


        firebaseFirestore.collection("registration")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result

                    result?.forEach { queryDocumentSnapshot ->
                        val registration = queryDocumentSnapshot.toObject(Registration::class.java)


                        if (registration.Gender != gender){
                            list.add(registration)
                        }


                        firebaseFirestore.collection("girls_response")
                            .get()
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val result = it.result

                                    result?.forEach { queryDocumentSnapshot ->
                                        val girsResponse = queryDocumentSnapshot.toObject(
                                            GirlsResponse::class.java)

                                        list2.add(girsResponse)

                                        adapter = RegistrationAdapter(list,firebaseFirestore,password,list2,object :RegistrationAdapter.OnItremClickListener{
                                            override fun onItemClick(
                                                malumot: Registration,
                                                matchPercentage: Int,
                                                matchedTraits: List<String>,
                                                unmatchedTraits: List<String>
                                            ) {
                                                Toast.makeText(binding.root.context, "First - ${matchedTraits[0]}", Toast.LENGTH_SHORT).show()
                                            }


                                        })
                                        binding.rv.adapter = adapter
                                        adapter.sortAndNotify()

                                    }
                                }}











                    }
                }}
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
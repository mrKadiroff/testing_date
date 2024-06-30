package com.example.testoviy_dating.bottomfragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.R
import com.example.testoviy_dating.adapter.RegistrationAdapter
import com.example.testoviy_dating.databinding.FragmentAccountBinding
import com.example.testoviy_dating.databinding.FragmentDashboardBinding
import com.example.testoviy_dating.models.Registration
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
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

    lateinit var binding: FragmentAccountBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()



        setUi()






        return binding.root
    }

    private fun setUi() {
        val password = (activity as? BottomActivity)?.intent?.getStringExtra("password")

        firebaseFirestore.collection("registration")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result

                    result?.forEach { queryDocumentSnapshot ->
                        val registration = queryDocumentSnapshot.toObject(Registration::class.java)


                        if (registration.Password == password){
                           binding.textViewName.text = registration.Name
                            binding.textViewSurname.text = registration.Surname
                            binding.textViewGender.text = registration.Gender


                            binding.questions.setOnClickListener {
                                if (registration.Gender == "Female"){
                                    findNavController().navigate(R.id.questionsFragment)
                                }else{
                                    findNavController().navigate(R.id.boysQuestionsFragment)
                                }
                            }



                        }



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
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
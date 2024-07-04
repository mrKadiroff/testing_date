package com.example.testoviy_dating.bottomfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.R
import com.example.testoviy_dating.adapter.QuestionsAdapter
import com.example.testoviy_dating.adapter.RegistrationAdapter2
import com.example.testoviy_dating.databinding.FragmentAboutBinding
import com.example.testoviy_dating.databinding.FragmentQuestionsBinding
import com.example.testoviy_dating.models.GirlsExpectation
import com.example.testoviy_dating.models.Registration
import com.example.testoviy_dating.newadapters.UsersAdapter
import com.example.testoviy_dating.newreg.BoysReg
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {
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

    lateinit var binding: FragmentAboutBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsersAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<BoysReg>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()



        return binding.root
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AboutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
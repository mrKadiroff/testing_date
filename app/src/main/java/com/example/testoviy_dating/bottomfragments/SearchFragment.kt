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
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.R
import com.example.testoviy_dating.databinding.FragmentDashboardBinding
import com.example.testoviy_dating.databinding.FragmentSearchBinding
import com.example.testoviy_dating.newadapters.SearchBoysAdapter
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
    private lateinit var adapter: SearchBoysAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<BoysReg>
    lateinit var list2: ArrayList<GirlsReg>
    private  val TAG = "SearchFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()


        val gender = (activity as? BottomActivity)?.intent?.getStringExtra("gender")
        val password = (activity as? BottomActivity)?.intent?.getStringExtra("password")


        if (gender == "Male"){
            Toast.makeText(binding.root.context, "YOu are male", Toast.LENGTH_SHORT).show()
            setRvForBoys()
            setSpinner()
        }else{
            Toast.makeText(binding.root.context, "YOu are Female", Toast.LENGTH_SHORT).show()
        }



        return binding.root
    }

    private fun setSpinner() {

    }

    private fun setRvForBoys() {
        fetchDataforBoys()
    }

    private fun fetchDataforBoys() {
        list2 = ArrayList()
        firebaseFirestore.collection("girl_reg")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->

                        // Convert document to BoysReg object
                        val girlReg = queryDocumentSnapshot.toObject(GirlsReg::class.java)
                        list2.add(girlReg)
                        adapter = SearchBoysAdapter(list2, object :SearchBoysAdapter.OnItremClickListener{
                            override fun onItemClick(malumotlar: GirlsReg) {

                            }

                        })

                        binding.rv.adapter = adapter

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
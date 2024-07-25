package com.example.testoviy_dating.bottomfragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.R
import com.example.testoviy_dating.adapter.RegistrationAdapter
import com.example.testoviy_dating.databinding.FragmentAccountBinding
import com.example.testoviy_dating.databinding.FragmentDashboardBinding
import com.example.testoviy_dating.models.Registration
import com.example.testoviy_dating.newadapters.InvitationsAdapter
import com.example.testoviy_dating.newadapters.UsersBoysAdapter
import com.example.testoviy_dating.newreg.BoysReg
import com.example.testoviy_dating.newreg.GirlsReg
import com.example.testoviy_dating.newreg.Invitation
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONObject

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
    lateinit var list: ArrayList<Invitation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        firebaseFirestore = FirebaseFirestore.getInstance()

        val check = (activity as? BottomActivity)?.intent?.getStringExtra("log")


        test()

        if (check == "registration") {
            val regInformation = (activity as? BottomActivity)?.intent?.getSerializableExtra("reg") as Registration
            binding.questions.setOnClickListener {
                Toast.makeText(binding.root.context, regInformation.Name, Toast.LENGTH_SHORT).show()

                if (regInformation.Gender == "Female") {
                    findNavController().navigate(R.id.questionsFragment)
                } else {
                    findNavController().navigate(R.id.boysQuestionsFragment)
                }
            }
        } else {
            val password = (activity as? BottomActivity)?.intent?.getStringExtra("password")
            Toast.makeText(binding.root.context, password, Toast.LENGTH_SHORT).show()
            respondToInvitation()
        }

        return binding.root
    }

    private fun test() {
        binding.profileImage.setOnClickListener {
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                        return@addOnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result









                }
        }



    }

    private fun respondToInvitation() {
        list = ArrayList()
        firebaseFirestore.collection("invitations")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->
                        val password = (activity as? BottomActivity)?.intent?.getStringExtra("password")
                        val invitation = queryDocumentSnapshot.toObject(Invitation::class.java).apply {
                            documentId = queryDocumentSnapshot.id
                        }

                        if (invitation.To == password) {
                            Toast.makeText(binding.root.context, invitation.From, Toast.LENGTH_SHORT).show()
                            list.add(invitation)
                        }
                    }
                    setupRecyclerView()
                } else {
                    Toast.makeText(binding.root.context, "Error getting documents.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setupRecyclerView() {
        val invitationsAdapter = InvitationsAdapter(
            list,
            object : InvitationsAdapter.OnItemClickListener {
                override fun onItemClick(malumotlar: Invitation) {
                    updateInvitationStatus(malumotlar.documentId!!, "accepted")
                }
            },
            object : InvitationsAdapter.OnItemDeclineClickListener {
                override fun onItemDeclineClick(malumotlar: Invitation) {
                    updateInvitationStatus(malumotlar.documentId!!, "declined")
                }
            },

            object : InvitationsAdapter.OnItemJustClickListener {
                override fun onItemJustClick(malumot: Invitation) {
                    val bundle = Bundle().apply {
                        putString("senderId", malumot.To)
                        putString("receiverId", malumot.From)
                    }
                    findNavController().navigate(R.id.chatFragment,bundle)
                }

            }
        )
        binding.rv.adapter = invitationsAdapter
    }

    private fun updateInvitationStatus(documentId: String, status: String) {
        val invitationRef = firebaseFirestore.collection("invitations").document(documentId)
        invitationRef.update("status", status)
            .addOnSuccessListener {
                Toast.makeText(binding.root.context, "Invitation $status!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(binding.root.context, "Failed to update invitation status.", Toast.LENGTH_SHORT).show()
            }
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
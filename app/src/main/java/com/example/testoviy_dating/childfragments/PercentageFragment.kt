package com.example.testoviy_dating.childfragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.R
import com.example.testoviy_dating.databinding.FragmentPercentageBinding
import com.example.testoviy_dating.databinding.FragmentRecommendationBinding
import com.example.testoviy_dating.models.GirlsExpectation
import com.example.testoviy_dating.models.GirlsResponse
import com.example.testoviy_dating.newreg.BoysReg
import com.example.testoviy_dating.newreg.GirlsReg
import com.example.testoviy_dating.newreg.Invitation
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PercentageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PercentageFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentPercentageBinding
    lateinit var firebaseFirestore: FirebaseFirestore

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPercentageBinding.inflate(layoutInflater, container, false)
        firebaseFirestore = FirebaseFirestore.getInstance()
//        val gender = arguments?.getString("gender")


            setStatistics()
        binding.sendInvitation.setOnClickListener {
            val boyUserData = arguments?.getSerializable("userDataBoy") as BoysReg
            val girlDataForBoyUser = arguments?.getSerializable("girlData") as GirlsReg
            sendInvitation(boyUserData.Password!!,girlDataForBoyUser.Password!!)
        }



        binding.gotoChafragment.setOnClickListener {

            val bundle = Bundle().apply {
                val boyUserData = arguments?.getSerializable("userDataBoy") as BoysReg
                val girlDataForBoyUser = arguments?.getSerializable("girlData") as GirlsReg
                putString("senderId", boyUserData.Password!!)
                putString("receiverId", girlDataForBoyUser.Password!!)
            }


            findNavController().navigate(R.id.chatFragment,bundle)
        }


        return binding.root
    }

    private fun sendInvitation(boyPassword: String, girlPassword: String) {
        // Create the invitation object with default values
        val invitation = Invitation(boyPassword, girlPassword, "pending", FieldValue.serverTimestamp().toString())

        // Add the invitation to Firestore
        firebaseFirestore.collection("invitations")
            .add(invitation)
            .addOnSuccessListener { documentReference ->
                // Get the generated documentId
                val documentId = documentReference.id

                // Update the invitation with the documentId
                firebaseFirestore.collection("invitations")
                    .document(documentId)
                    .update("documentId", documentId)
                    .addOnSuccessListener {
                        Toast.makeText(binding.root.context, "Invitation sent with ID: $documentId", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(binding.root.context, "Failed to update invitation with documentId.", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(binding.root.context, "Failed to send invitation.", Toast.LENGTH_SHORT).show()
            }
    }


    //set data about girls matching
    private fun setStatistics() {
        val percentage = arguments?.getInt("percentage")
        val boyUserData = arguments?.getSerializable("userDataBoy") as BoysReg
        val girlDataForBoyUser = arguments?.getSerializable("girlData") as GirlsReg

        val boyExpectations = boyUserData.BoysExpectation
        val girlResponses = girlDataForBoyUser.GirlsResponse

        binding.kettu.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("girlExpectations", girlDataForBoyUser.GirlsExpectation)
                putSerializable("boyResponse", boyUserData.BoysResponse)
            }
            findNavController().navigate(R.id.percentageChildFragment, bundle)
        }

        val questions = listOf(
            "1. Juftingiz qanday bo'lishini hohlaysiz?",
            "2. Ma'lumoti qanday bo'lsin?",
            "3. To'ydan keyin ishlashiga qanday munosabatdasiz?",
            "4. Bo'yi uzunligi qanday bo'lishini hohlaysiz?",
            "5. Vazni qanday bo'lishini hohlaysiz?",
            "6. Yuz tuzilishi qanday bo'lishini hohlaysiz?",
            "7. Sizga nisbatan qanday munosabatda bo'lishini hohlaysiz?",
            "8. Asosiy prioritetlari qanday bo'lishini hohlaysiz?"
        )

        val wordQuestions = listOf(
            "9. Some question for Ninth expectation",
            "10. Some question for Tenth expectation",
            "11. Some question for Eleventh expectation",
            "12. Some question for Twelveth expectation",
            "13. Some question for Thirteenth expectation",
            "14. Some question for Fourteenth expectation",
            "15. Some question for Fifteenth expectation",
            "16. Some question for Sixteenth expectation"
        )

        val boyWords = listOf(
            boyExpectations?.Ninth, boyExpectations?.Tenth, boyExpectations?.Eleventh, boyExpectations?.Twelveth,
            boyExpectations?.Thirteenth, boyExpectations?.Fourteenth, boyExpectations?.Fifteenth, boyExpectations?.Sixteenth
        )

        val girlWords = listOf(
            girlResponses?.Ninth, girlResponses?.Tenth, girlResponses?.Eleventh, girlResponses?.Twelveth,
            girlResponses?.Thirteenth, girlResponses?.Fourteenth, girlResponses?.Fifteenth, girlResponses?.Sixteenth
        )

        val matchedTraits = StringBuilder("Matched Traits:\n")
        val unmatchedTraits = StringBuilder("Unmatched Traits:\n")

        var matchedCount = 0
        val totalCount = questions.size + wordQuestions.size

        questions.forEachIndexed { index, question ->
            val boyAnswer = getAnswerText(boyExpectations, index + 1)
            val girlAnswer = getAnswerText(girlResponses, index + 1)

            if (boyAnswer == girlAnswer) {
                matchedTraits.append("$question\nBoy's Response: $boyAnswer\nGirl's Response: $girlAnswer\n\n")
                matchedCount++
            } else {
                unmatchedTraits.append("$question\nBoy's Response: $boyAnswer\nGirl's Response: $girlAnswer\n\n")
            }
        }

        boyWords.forEachIndexed { index, boyWord ->
            val question = wordQuestions[index]
            val matched = girlWords.any { girlWord -> boyWord == girlWord }
            if (matched) {
                matchedTraits.append("$question\nBoy's Response: $boyWord\nGirl's Response: Matched\n\n")
                matchedCount++
            } else {
                unmatchedTraits.append("$question\nBoy's Response: $boyWord\nGirl's Response: Not Matched\n\n")
            }
        }

        val overallMatchingPercentage = (matchedCount.toDouble() / totalCount) * 100
        binding.matchings.text = "$matchedTraits\n$unmatchedTraits"
        binding.percentageee.text = "Overall Matching Percentage: ${"%.2f".format(overallMatchingPercentage)}%"
    }

    private fun getAnswerText(response: GirlsResponse?, questionIndex: Int): String {
        return when (questionIndex) {
            1 -> when (response?.First) {
                "A" -> "Hijob o'ragan, dinga e'tiqodi baland."
                "B" -> "Dunyoviy, zamonaviy kiyingan."
                "C" -> "Ham diniy, ham dunyoviy."
                else -> ""
            }
            2 -> when (response?.Second) {
                "A" -> "Oliy ma'lumotli."
                "B" -> "O'rta maxsus."
                "C" -> "O'rta maxsus lekin hunarli."
                else -> ""
            }
            3 -> when (response?.Third) {
                "A" -> "Ishlamoqchiman."
                "B" -> "Yo'q, uyda o'tirmoqchiman."
                "C" -> "Oilani iqtisodiy holatiga qarab ishlayman yoki ishlamayman."
                else -> ""
            }
            4 -> when (response?.Fourth) {
                "A" -> "Past(1.50 dan 1.60)"
                "B" -> "O'rta (1.60 dan 1.70 gacha)."
                "C" -> "Baland (1.70+)."
                else -> ""
            }
            5 -> when (response?.Fifth) {
                "A" -> "Yengil(45kg dan 60kg gacha)."
                "B" -> "O'rta vazn(60 kgdan 70 kggacha)."
                "C" -> "Og'ir(70+)."
                else -> ""
            }
            6 -> when (response?.Sixth) {
                "A" -> "Oq-sariq, ko'k-yashil ko'zli."
                "B" -> "Qora qosh, qora ko'zli, bug'doy rang."
                "C" -> "Jingalak sochli va o'ziga hos jihatlarga ega."
                else -> ""
            }
            7 -> when (response?.Seventh) {
                "A" -> "Doim qo'llab quvvatlayman, unga ishonanan. Qiyin paytida tashlab ketmayman."
                "B" -> "Meni o'zimni hayotim, o'z prinsiplarim bor. Urush-janjal bo'lib turishi mumkin."
                "C" -> "Vaqtinchalik munosabatlarda bo'lishini. Ma'lum bir muddatdan keyin ajrashib ketamiz."
                else -> ""
            }
            8 -> when (response?.Eigth) {
                "A" -> "Oilam, farzandlarim deydigan qizman."
                "B" -> "Koryerani birinchi o'rinda qo'yaman"
                "C" -> "Ham oila va koryerani teng olib keta olaman."
                else -> ""
            }
            else -> ""
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as BottomActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as BottomActivity).showBottomNavigation()
        super.onDetach()

    }

    override fun onResume() {
        (activity as BottomActivity).hideBottomNavigation()
        super.onResume()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PercentageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

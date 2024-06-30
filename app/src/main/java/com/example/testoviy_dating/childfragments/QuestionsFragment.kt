package com.example.testoviy_dating.childfragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.R
import com.example.testoviy_dating.adapter.QuestionsAdapter
import com.example.testoviy_dating.adapter.TraitsAdapter
import com.example.testoviy_dating.databinding.FragmentQuestionsBinding
import com.example.testoviy_dating.models.BoysTraits
import com.example.testoviy_dating.models.GirlsExpectation
import com.example.testoviy_dating.models.Question
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionsFragment : Fragment() {
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

    lateinit var binding: FragmentQuestionsBinding
    lateinit var list: ArrayList<GirlsExpectation>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionsAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var traitsAdapter: TraitsAdapter
    private val traitsList = mutableListOf<BoysTraits>()
    private val questions = listOf(
        Question("1. Oylik daromadi qancha bo'lishini hohlaysiz?", listOf(". 2-4 million so'm", "5-9 million so'm.", "10mln +.")),
        Question("2.Yuz tuzilishi qanday bo'lishini hohlaysiz?", listOf("Oq-sariq yuzli , ko'k-yashil ko'zli", "Qora sochli, qora qoshli, qora ko'zli.", "Jingalak sochli va o'ziga xos xususiyatli.")),
        Question("Bo'yi uzunligi?", listOf("Past bo'yli (1,50 dan 1.60 gacha)", "O'rta bo'yli (1.60 dan 1.75 gacha)", "Uzun bo'yli(1.75 +)")),
        Question("E'tiqodi.", listOf("Islom diniga e'tiqodi kuchli", "Xudo borligini biladi lekin dunyoviy odam.", "Hech qanday dinga e'tiqod qilmaydi (Dinsiz)")),
        Question("Tanishuvdan maqsadi nima bo'lishini istaysiz?", listOf("Niyati qat'iy oila qurish.", "Shunchaki do'stlashib, yangi tanish orttirish ", "Zerikkandan odam qidirayotgan.")),
        Question("Ma'lumoti.", listOf("Oliy ma'lumotli.", "O'rta maxsus.", "O'rta maxsus lekin òz hunar yoki ishiga ega")),
        Question("Kasbi qanday bo'lishini xohlaysiz?", listOf("Davlat ishida(Bank, Shifoxona, Universitet, Soliq idorasi, maktab.....)", "Tadbirkor.", "Hozirgi zamonaviy kasblar bilan shug'ullanadigan")),
        Question("Sizga qanday munosabatda bo'lishini istaysiz?", listOf("Mehribon, istaklarimni qo'llab quvvatlaydigan, rivojlanishimga imkon beradigan", "Nazoratni to'liq qo'lga oladigan, dominant erkak lekin meni hurmat qilishi kerak.", "Uni aytganini so'zsiz bajaraman.")),
        Question("Hayotdagi prioritetlari qanday bo'lishi kerak?", listOf("Koryerani birinchi o'ringa qo'yadigan, o'zini sohasi va pul topishga bag'ishlagan", "Koryera va oilani teng olib ketishga harakat qiladigan", "Koryerasidan kòra menga kòproq e'tibor qaratadigan.")),

        // Add more questions here
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuestionsBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()

        setQuestions()
        setTraits()







        return binding.root
    }

    private fun setTraits() {
        val traitsNames = listOf("Aqlli", "Diniy bilimlarga ega","eʼtiborli", "Faxm farosatli", "Hazilkash", "Harakatchan, izlanuvchan", "Iymonli diyonatli",
            "Ichida gina saqlamaydigan", "Intellektual","Kelishgan","Kamgap","Mehnatsevar","Mehribon","Oʻqimishli","O'ziga ishonchi baland", "ochiq ko'ngil",
            "Pozitiv","Qattiqqo'l","Qaysar","Romantik","Rostgo'y","Sadoqatli","Samimiy","Sayohatlarga qiziqadigan","So'zini ustidan chiqadigan",
            "Suyansa bo'ladigan","Syurprizlar qiladigan","Tinglashni yaxshi ko'radigan","Tushunadigan, ishonadigan","Vaziyatga sovuqqonlik bilan munosabat bildiradigan",
            "Vafoli","Xushmuomala",/* ... add more traits ... */)
        traitsList.addAll(traitsNames.map { BoysTraits(it) })

        var traitsRecyclerView = binding.traitsRecyclerView
        traitsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        traitsAdapter = TraitsAdapter(traitsList)
        traitsRecyclerView.adapter = traitsAdapter


    }



    private fun setQuestions() {
        recyclerView = binding.recyclerView
        adapter = QuestionsAdapter(questions)

        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.adapter = adapter


        // Add a button to save results
        val saveButton: Button = binding.saveButton
        saveButton.setOnClickListener {

            val selectedTraits = traitsAdapter.getSelectedTraits()
            if (selectedTraits.size == 5) {
                // Save the selected traits
                val results = adapter.getResults()
                saveResults(results,selectedTraits)


            } else {
                Toast.makeText(binding.root.context, "Please select exactly 5 traits.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveResults(results: List<String>, selectedTraits: List<BoysTraits>) {
        val password = (activity as? BottomActivity)?.intent?.getStringExtra("password")

        val girlsExpectation = GirlsExpectation(password, results[0],results[1],results[2],results[3],results[4],results[5],
            results[6],results[7],results[8],selectedTraits[0].name,selectedTraits[1].name,selectedTraits[2].name,selectedTraits[3].name,selectedTraits[4].name,)

        firebaseFirestore.collection("girls_expectation")
            .add(girlsExpectation)
            .addOnSuccessListener {
                Toast.makeText(
                    binding.root.context,
                    "Succesfully saved",
                    Toast.LENGTH_SHORT
                )
                    .show()

                findNavController().navigate(R.id.girlsResponseFragment)



            }.addOnFailureListener {
                Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
            }





        // Save results as needed. For example, log them or save to a file.
//        results.forEachIndexed { index, result ->
//            Toast.makeText(binding.root.context, "TestResults\", \"Question ${index + 1}: $result", Toast.LENGTH_LONG).show()
//
//        }
    }

    override fun onResume() {
        super.onResume()
        (activity as BottomActivity).hideBottomNavigation()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as BottomActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as BottomActivity).showBottomNavigation()
        super.onDetach()

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
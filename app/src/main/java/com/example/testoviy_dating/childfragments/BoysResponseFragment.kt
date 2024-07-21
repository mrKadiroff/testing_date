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
import com.example.testoviy_dating.databinding.FragmentBoysResponseBinding
import com.example.testoviy_dating.databinding.FragmentQuestionsBinding
import com.example.testoviy_dating.models.BoysTraits
import com.example.testoviy_dating.models.GirlsExpectation
import com.example.testoviy_dating.models.GirlsResponse
import com.example.testoviy_dating.models.Question
import com.example.testoviy_dating.models.Registration
import com.example.testoviy_dating.newreg.BoysReg
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BoysResponseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BoysResponseFragment : Fragment() {
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

    lateinit var binding: FragmentBoysResponseBinding
    lateinit var list: ArrayList<GirlsExpectation>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionsAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var traitsAdapter: TraitsAdapter
    private val traitsList = mutableListOf<BoysTraits>()
    private val questions = listOf(
        Question("1. Oylik daromadingiz qancha?", listOf(". 2-4 million so'm", "5-9 million so'm.", "10mln +.")),
        Question("2.Yuz tuzilishingiz qanday?", listOf("Oq-sariq yuzli , ko'k-yashil ko'zli", "Qora sochli, qora qoshli, qora ko'zli.", "Jingalak sochli va o'ziga xos xususiyatli.")),
        Question("Bo'yingiz uzunligi?", listOf("Past bo'yli (1,50 dan 1.60 gacha)", "O'rta bo'yli (1.60 dan 1.75 gacha)", "Uzun bo'yli(1.75 +)")),
        Question("E'tiqodingiz...", listOf("Islom diniga e'tiqodi kuchli", "Xudo borligini biladi lekin dunyoviy odam.", "Hech qanday dinga e'tiqod qilmaydi (Dinsiz)")),
        Question("Tanishuvdan maqsadingiz nima?", listOf("Niyatim qat'iy oila qurish.", "Shunchaki do'stlashib, yangi tanish orttirish ", "Zerikkandan odam qidirayapman.")),
        Question("Ma'lumotingiz qanday?", listOf("Oliy ma'lumotli.", "O'rta maxsus.", "O'rta maxsus lekin òz hunar yoki ishiga ega")),
        Question("Kasbingiz qanday?", listOf("Davlat ishida(Bank, Shifoxona, Universitet, Soliq idorasi, maktab.....)", "Tadbirkor.", "Hozirgi zamonaviy kasblar bilan shug'ullanadigan")),
        Question("Bo'lajak partnyoringizga nisbatan qanday munosabatda bo'lasiz?", listOf(". Mehribon, uning istaklarini qo'llab quvvatlayman, rivojlanishiga imkon beraman.", "Nazoratni to'liq qo'lga oladigan dominant erkak bo'laman lekin uni hurmat qilaman.", "Aytganimni so'zsiz bajarishi kerak.")),
        Question("Hayotdagi prioritetlaringiz qanday?", listOf("Koryerani birinchi o'ringa qo'yadigan, o'zimni sohamga va pul topishga bag'ishlayman.", "Koryera va oilani teng olib ketishga harakat qilaman.", "Koryeradan kòra ayolimga ko'proq e'tibor beraman.")),

        // Add more questions here
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBoysResponseBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()


        setQuestions()
        settraits()

        val regInformation = (activity as? BottomActivity)?.intent?.getSerializableExtra("reg") as Registration
        binding.nextcha.setOnClickListener {
            Toast.makeText(binding.root.context, regInformation.Password, Toast.LENGTH_SHORT).show()
        }








        return binding.root
    }

    private fun settraits() {
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


        val bundle = arguments
        val boysExpectations: GirlsResponse? = bundle?.getSerializable("expectation") as GirlsResponse?
        val regInformation = (activity as? BottomActivity)?.intent?.getSerializableExtra("reg") as Registration


        val boyResponse = GirlsExpectation( results[0],results[1],results[2],results[3],results[4],results[5],
            results[6],results[7],results[8],selectedTraits[0].name,selectedTraits[1].name,selectedTraits[2].name,selectedTraits[3].name,selectedTraits[4].name,)




        val boysReg = BoysReg(regInformation.Name,regInformation.Surname,regInformation.Age,regInformation.Gender,regInformation.Password,regInformation.RecoveryAccount,
            regInformation.Region,boysExpectations,boyResponse)

        firebaseFirestore.collection("boy_reg")
            .add(boysReg)
            .addOnSuccessListener {
                Toast.makeText(
                    binding.root.context,
                    "Succesfully saved",
                    Toast.LENGTH_SHORT
                )
                    .show()

                findNavController().navigate(R.id.accountFragment)



            }.addOnFailureListener {
                Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BoysResponseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BoysResponseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
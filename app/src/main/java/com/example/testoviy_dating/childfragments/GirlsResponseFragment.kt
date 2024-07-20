package com.example.testoviy_dating.childfragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.BottomActivity
import com.example.testoviy_dating.R
import com.example.testoviy_dating.adapter.InterestsAdapter
import com.example.testoviy_dating.adapter.QuestionsAdapter
import com.example.testoviy_dating.adapter.TraitsAdapter
import com.example.testoviy_dating.databinding.FragmentGirlsResponseBinding
import com.example.testoviy_dating.databinding.FragmentQuestionsBinding
import com.example.testoviy_dating.models.BoysTraits
import com.example.testoviy_dating.models.GirlsExpectation
import com.example.testoviy_dating.models.GirlsResponse
import com.example.testoviy_dating.models.GirlsTraits
import com.example.testoviy_dating.models.Question
import com.example.testoviy_dating.models.Registration
import com.example.testoviy_dating.newreg.GirlsReg
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GirlsResponseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GirlsResponseFragment : Fragment() {
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionsAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var binding: FragmentGirlsResponseBinding
    private lateinit var traitsAdapter: TraitsAdapter
    private lateinit var interestsAdapter: InterestsAdapter
    private val traitsList = mutableListOf<BoysTraits>()
    private val interestList = mutableListOf<BoysTraits>()
    private val questions = listOf(
        Question("1. O'zingizni qanday tasvirlaysiz?", listOf("Hijob o'ragan, dinga e'tiqodi baland.", "Dunyoviy, zamonaviy kiyingan", "Ham diniy, ham dunyoviy.")),
        Question("2. Ma'lumotingiz qanday?", listOf("Oliy ma'lumotli.", "O'rta maxsus.", "O'rta maxsus lekin hunarli.")),
        Question("3. To'ydan keyingi hayot haqidagi fikrlaringiz?", listOf("Ishlamoqchiman.", "Yo'q, uyda o'tirmoqchiman.", "Oilani iqtisodiy holatiga qarab ishlayman yoki ishlamayman")),
        Question("4. Bo'y uzunligingiz qanday?", listOf("Past(1.50 dan 1.60)", "O'rta (1.60 dan 1.70 gacha).", "Baland (1.70+).")),
        Question("5. Vazningiz qanday?", listOf("A. Yengil(45kg dan 60kg gacha).", "O'rta vazn(60 kgdan 70 kggacha).", "Og'ir(70+).")),
        Question("6. Yuz tuzilishingiz qanday?", listOf("Oq-sariq, ko'k-yashil ko'zli. ", "Qora qosh, qora ko'zli, bug'doy rang.", "Jingalak sochli va o'ziga hos jihatlarga ega.")),
        Question("7. Bo'lajak partnyoringizga nisbatan qanday munosabatda bo'lasiz?", listOf("Doim qo'llab quvvatlayman, unga ishonanan. Qiyin paytida tashlab ketmayman.", "Meni o'zimni hayotim, o'z prinsiplarim bor. Urush-janjal bo'lib turishi mumkin.", "Vaqtinchalik munosabatlarda bo'lishini. Ma'lum bir muddatdan keyin ajrashib ketamiz.")),
        Question("8. Asosiy prioritetlaringiz qanday?", listOf(". Oilam, farzandlarim deydigan qizman.", "Koryerani birinchi o'rinda qo'yaman", "Ham oila va koryerani teng olib keta olaman.")),
        // Add more questions here
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGirlsResponseBinding.inflate(layoutInflater,container,false)
        firebaseFirestore = FirebaseFirestore.getInstance()



        setQuestions()
        setTraits()
        setInterests()

        return binding.root
    }

    private fun setInterests() {

        val interestNames = listOf("Moda(fashion)", "Pazandalik", "Sayohat", "San'at", "Musiqa", "Adabiyot","Fitness", "Dugonalar davrasida vaqt o'tkazish","Bayram va festivallarga qatmashish",
            "Fotograflik", "Gullar parvarishlash", "Volantyorlik", "Tikish-bichish", "Pardoz-andoz qilish", "kino ko'rish", "Til o'rganish", "Din", "Blog yoki vlog yuritish",
            "Tabiat qo'ynida sayir qilish", "Raqs", "Shopping", "Ichki dizayn(interiror design)", "Yoga", "Teatrga borish", "Hunarmandchilik", "Buyumlarni kollektsiya qilish",
            "Uy hayvonlarini boqish", "Tabiatni toza saqlashga harakat qilish")


        interestList.addAll(interestNames.map { BoysTraits(it) })

        var interstRecyclerView = binding.interestRecyclerView
        interstRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        interestsAdapter = InterestsAdapter(interestList)
        interstRecyclerView.adapter = interestsAdapter
    }

    private fun setTraits() {
        val traitsNames = listOf("Kamtar", "Oilaparvar", "Sadoqatli","Sabrli", "Kamgap", "Xazilkash", "Xazilni qabul qilad oladigan","Odobli ,tartible",
            "Mehnatkash", "Samimiy","Kamtar","Moslashuvchan", "G'amxo'r","Jamiyat faoli", "Yaxshi tinglovchi","Ishonsa bo'ladigan, sir saqlay oladigan",
            "Hissiy intelekti yuqori (Emotional intelligence)", "Optimist", "Shukur qiladigan", "Introvert", "Extrovert", "Shustri", "Sodda" /* ... add more traits ... */)


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


        binding.saveButton.setOnClickListener {
            val selectedTraits = traitsAdapter.getSelectedTraits()
            val selectedInterests = interestsAdapter.getSelectedTraits()
            if (selectedTraits.size == 5 && selectedInterests.size == 3) {
                // Save the selected traits
                val results = adapter.getResults()
                saveResults(results,selectedTraits,selectedInterests)


            } else {
                Toast.makeText(binding.root.context, "Please select exactly 5 traits. and 3 interests", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveResults(
        results: List<String>,
        selectedTraits: List<BoysTraits>,
        selectedInterests: List<BoysTraits>
    ) {
        val password = (activity as? BottomActivity)?.intent?.getStringExtra("password")


        val bundle = arguments
        val girlsExpectations = bundle?.getSerializable("g_exp") as GirlsExpectation?
        val regInformation = (activity as? BottomActivity)?.intent?.getSerializableExtra("reg") as Registration



        val girlsResponse = GirlsResponse(results[0],results[1],results[2],results[3],results[4],results[5],
            results[6],results[7],selectedTraits[0].name,selectedTraits[1].name,selectedTraits[2].name,selectedTraits[3].name,selectedTraits[4].name,
            selectedInterests[0].name,selectedInterests[1].name,selectedInterests[2].name)


        var girlsReg = GirlsReg(regInformation.Name,regInformation.Surname,regInformation.Age,regInformation.Gender,regInformation.Password,regInformation.RecoveryAccount,regInformation.Region,
            girlsExpectations,girlsResponse)


        firebaseFirestore.collection("girl_reg")
            .add(girlsReg)
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
         * @return A new instance of fragment GirlsResponseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GirlsResponseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
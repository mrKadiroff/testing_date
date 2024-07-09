package com.example.testoviy_dating.childfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testoviy_dating.R
import com.example.testoviy_dating.databinding.FragmentPercentageBinding
import com.example.testoviy_dating.databinding.FragmentPercentageChildBinding
import com.example.testoviy_dating.models.GirlsExpectation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PercentageChildFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PercentageChildFragment : Fragment() {

    lateinit var binding: FragmentPercentageChildBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPercentageChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the serializable objects from arguments
        val girlsExpectation = arguments?.getSerializable("girlExpectations") as GirlsExpectation
        val boysResponses = arguments?.getSerializable("boyResponse") as GirlsExpectation

        // Calculate matching percentage and results
        GlobalScope.launch {
            val (matchingPercentage, matchedResults, unmatchedResults) = calculateMatchingPercentage(girlsExpectation, boysResponses)

            // Update UI on the main thread
            launch(Dispatchers.Main) {
                binding.percentage.text = "Overall Matching Percentage: $matchingPercentage%"

                // Display matched results
                val matchedStringBuilder = StringBuilder()
                matchedStringBuilder.append("Matched Questions:\n")
                matchedResults.forEach {
                    matchedStringBuilder.append("Question ${it.questionNumber}: ${it.question}\n")
                    matchedStringBuilder.append("  Girl's Answer: ${it.girlAnswer}\n")
                    matchedStringBuilder.append("  Boy's Answer: ${it.boyAnswer}\n")
                    matchedStringBuilder.append("\n")
                }
                binding.expectations.text = matchedStringBuilder.toString()

                // Display unmatched results
                val unmatchedStringBuilder = StringBuilder()
                unmatchedStringBuilder.append("Unmatched Questions:\n")
                unmatchedResults.forEach {
                    unmatchedStringBuilder.append("Question ${it.questionNumber}: ${it.question}\n")
                    unmatchedStringBuilder.append("  Girl's Answer: ${it.girlAnswer}\n")
                    unmatchedStringBuilder.append("  Boy's Answer: ${it.boyAnswer}\n")
                    unmatchedStringBuilder.append("\n")
                }
                binding.response.text = unmatchedStringBuilder.toString()
            }
        }
    }

    private fun calculateMatchingPercentage(girlsExpectation: GirlsExpectation, boysResponse: GirlsExpectation): Triple<Double, List<ComparisonResult>, List<ComparisonResult>> {
        val matchedResults = mutableListOf<ComparisonResult>()
        val unmatchedResults = mutableListOf<ComparisonResult>()
        var matchCount = 0
        val totalCount = 14 // We have 14 fields to compare

        // Mapping questions to human-readable text responses
        val questions = listOf(
            "Oylik daromadingiz qancha?",
            "Yuz tuzilishingiz qanday?",
            "Bo'yingiz balandligi?",
            "E'tiqodi",
            "Tanishuvdan maqsadingiz nima?",
            "Ma'lumoti",
            "Kasbingiz qanday?",
            "Bo'lajak partnyoringizga nisbatan qanday munosabatda bo'lasiz?",
            "Hayotdagi prioritetlaringiz qanday?",
            "Xarakter",
            "Xarakter 2",
            "Xarakter 3",
            "Xarakter 4",
            "Xarakter 5"
        )

        // Compare first 9 fields directly
        val fieldsDirectComparison = listOf(
            girlsExpectation.First to boysResponse.First,
            girlsExpectation.Second to boysResponse.Second,
            girlsExpectation.Third to boysResponse.Third,
            girlsExpectation.Fourth to boysResponse.Fourth,
            girlsExpectation.Fifth to boysResponse.Fifth,
            girlsExpectation.Sixth to boysResponse.Sixth,
            girlsExpectation.Seventh to boysResponse.Seventh,
            girlsExpectation.Eigth to boysResponse.Eigth,
            girlsExpectation.Ninth to boysResponse.Ninth
        )

        for ((index, pair) in fieldsDirectComparison.withIndex()) {
            val (girlResponse, boyResponse) = pair
            val matched = girlResponse == boyResponse
            if (matched) {
                matchCount++
                matchedResults.add(
                    ComparisonResult(
                        index + 1,
                        questions[index],
                        getReadableAnswer(index + 1, girlResponse),
                        getReadableAnswer(index + 1, boyResponse),
                        matched
                    )
                )
            } else {
                unmatchedResults.add(
                    ComparisonResult(
                        index + 1,
                        questions[index],
                        getReadableAnswer(index + 1, girlResponse),
                        getReadableAnswer(index + 1, boyResponse),
                        matched
                    )
                )
            }
        }

        // Compare word-based responses for fields 10 to 14
        val boysResponsesList = listOf(boysResponse.Tenth, boysResponse.Eleventh, boysResponse.Twelveth, boysResponse.Thirteenth, boysResponse.Fourteenth)
        val fieldsWordBasedComparison = listOf(
            girlsExpectation.Tenth,
            girlsExpectation.Eleventh,
            girlsExpectation.Twelveth,
            girlsExpectation.Thirteenth,
            girlsExpectation.Fourteenth
        )

        for ((index, girlResponse) in fieldsWordBasedComparison.withIndex()) {
            val matched = boysResponsesList.contains(girlResponse)
            if (matched) {
                matchCount++
                matchedResults.add(
                    ComparisonResult(
                        index + 10,
                        questions[index + 9],
                        getReadableAnswer(index + 10, girlResponse),
                        getReadableAnswer(index + 10, girlResponse),
                        matched
                    )
                )
            } else {
                unmatchedResults.add(
                    ComparisonResult(
                        index + 10,
                        questions[index + 9],
                        getReadableAnswer(index + 10, girlResponse),
                        "No Match",
                        matched
                    )
                )
            }
        }

        // Calculate the matching percentage
        val matchingPercentage = (matchCount.toDouble() / totalCount) * 100

        return Triple(matchingPercentage, matchedResults, unmatchedResults)
    }

    // Helper function to get human-readable answer
    private fun getReadableAnswer(questionNumber: Int, answer: String?): String? {
        return when (questionNumber to answer) {
            1 to "A" -> "2-4 million so'm"
            1 to "B" -> "5-9 million so'm"
            1 to "C" -> "10mln +"
            2 to "A" -> "Oq-sariq yuzli , ko'k-yashil ko'zli"
            2 to "B" -> "Qora sochli, qora qoshli, qora ko'zli"
            2 to "C" -> "Jingalak sochli va o'ziga xos xususiyatli"
            3 to "A" -> "Past bo'yli (1,50 dan 1.60 gacha)"
            3 to "B" -> "O'rta bo'yli (1.60 dan 1.75 gacha)"
            3 to "C" -> "Uzun bo'yli(1.75 +)"
            4 to "A" -> "Islom diniga e'tiqodi kuchli"
            4 to "B" -> "Xudo borligini biladi lekin dunyoviy odam"
            4 to "C" -> "Hech qanday dinga e'tiqod qilmaydi (Dinsiz)"
            5 to "A" -> "Niyatim qat'iy oila qurish"
            5 to "B" -> "Shunchaki do'stlashib, yangi tanish orttirish"
            5 to "C" -> "Zerikkandan odam qidirayapman"
            6 to "A" -> "Oliy ma'lumotli"
            6 to "B" -> "O'rta maxsus"
            6 to "C" -> "O'rta maxsus lekin òz ishi yoki hunariga ega"
            7 to "A" -> "Davlat ishida(Bank, Shifoxona, Universitet, Soliq idorasi, maktab.....)"
            7 to "B" -> "Tadbirkor"
            7 to "C" -> "Hozirgi zamonaviy kasblar bilan shug'ullanaman"
            8 to "A" -> "Mehribon, uning istaklarini qo'llab quvvatlayman, rivojlanishiga imkon beraman"
            8 to "B" -> "Nazoratni to'liq qo'lga oladigan dominant erkak bo'laman lekin uni hurmat qilaman"
            8 to "C" -> "Aytganimni so'zsiz bajarishi kerak"
            9 to "A" -> "Koryerani birinchi o'ringa qo'yadigan, o'zimni sohamga va pul topishga bag'ishlayman"
            9 to "B" -> "Koryera va oilani teng olib ketishga harakat qilaman"
            9 to "C" -> "Koryeradan kòra ayolimga ko'proq e'tibor beraman"
            else -> answer // fallback to answer itself if no mapping found
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PercentageChildFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

data class ComparisonResult(
    val questionNumber: Int,
    val question: String,
    val girlAnswer: String?,
    val boyAnswer: String?,
    val matched: Boolean
)
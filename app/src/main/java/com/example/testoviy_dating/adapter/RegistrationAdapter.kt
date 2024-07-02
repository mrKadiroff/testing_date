package com.example.testoviy_dating.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.databinding.RegistrationLayoutBinding
import com.example.testoviy_dating.models.GirlsResponse
import com.example.testoviy_dating.models.Registration
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationAdapter(
    var list: List<Registration>,
    var firebaseFirestore: FirebaseFirestore,
    var password: String?,
    var girsResponse: ArrayList<GirlsResponse>,
    var onItremClickListener: OnItremClickListener
) : RecyclerView.Adapter<RegistrationAdapter.Vh>() {

    private var sortedResponses: List<Triple<Int, Registration, Pair<List<String>, List<String>>>> = emptyList()

    inner class Vh(var itemUserBinding: RegistrationLayoutBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        @SuppressLint("SuspiciousIndentation")
        fun onBind(malumot: Registration, matchPercentage: Int, matchedTraits: List<String>, unmatchedTraits: List<String>) {
            itemUserBinding.namecha.text = malumot.Name
            itemUserBinding.surnamecha.text = malumot.Surname
            itemUserBinding.gender.text = "$matchPercentage% match"

            itemView.setOnClickListener {
                onItremClickListener.onItemClick(malumot, matchPercentage, matchedTraits, unmatchedTraits)
            }
        }

        private fun calculateMatchData(boy: GirlsResponse, girl: GirlsResponse): Triple<Int, List<String>, List<String>> {
            val boyAttributes = listOf(
                boy.First, boy.Second, boy.Third, boy.Fourth,
                boy.Fifth, boy.Sixth, boy.Seventh, boy.Eigth,
                boy.Ninth, boy.Tenth, boy.Eleventh, boy.Twelveth,
                boy.Thirteenth, boy.Fourteenth, boy.Fifteenth, boy.Sixteenth
            )

            val girlAttributes = listOf(
                girl.First, girl.Second, girl.Third, girl.Fourth,
                girl.Fifth, girl.Sixth, girl.Seventh, girl.Eigth,
                girl.Ninth, girl.Tenth, girl.Eleventh, girl.Twelveth,
                girl.Thirteenth, girl.Fourteenth, girl.Fifteenth, girl.Sixteenth
            )

            val total = boyAttributes.size
            var matches = 0

            val matchedTraits = mutableListOf<String>()
            val unmatchedTraits = mutableListOf<String>()

            for (i in boyAttributes.indices) {
                if (boyAttributes[i]?.equals(girlAttributes[i], ignoreCase = true) == true) {
                    matches++
                    matchedTraits.add(boyAttributes[i] ?: "")
                } else {
                    unmatchedTraits.add(boyAttributes[i] ?: "")
                }
            }

            val matchPercentage = ((matches.toDouble() / total) * 100).toInt()
            return Triple(matchPercentage, matchedTraits, unmatchedTraits)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RegistrationLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val (matchPercentage, registration, matchData) = sortedResponses[position]
        val (matchedTraits, unmatchedTraits) = matchData
        holder.onBind(registration, matchPercentage, matchedTraits, unmatchedTraits)
    }

    override fun getItemCount(): Int {
        return sortedResponses.size
    }

    fun sortAndNotify() {
        firebaseFirestore.collection("boys_expectation")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result

                    result?.forEach { queryDocumentSnapshot ->
                        val boysExpecattions = queryDocumentSnapshot.toObject(GirlsResponse::class.java)

                        if (boysExpecattions.Password == password) {
                            sortedResponses = list.mapIndexed { index, registration ->
                                val (matchPercentage, matchedTraits, unmatchedTraits) = calculateMatchData(boysExpecattions, girsResponse[index])
                                Triple(matchPercentage, registration, Pair(matchedTraits, unmatchedTraits))
                            }.sortedByDescending { it.first }

                            notifyDataSetChanged()
                        }
                    }
                }
            }
    }

    private fun calculateMatchData(boy: GirlsResponse, girl: GirlsResponse): Triple<Int, List<String>, List<String>> {
        val boyAttributes = listOf(
            boy.First, boy.Second, boy.Third, boy.Fourth,
            boy.Fifth, boy.Sixth, boy.Seventh, boy.Eigth,
            boy.Ninth, boy.Tenth, boy.Eleventh, boy.Twelveth,
            boy.Thirteenth, boy.Fourteenth, boy.Fifteenth, boy.Sixteenth
        )

        val girlAttributes = listOf(
            girl.First, girl.Second, girl.Third, girl.Fourth,
            girl.Fifth, girl.Sixth, girl.Seventh, girl.Eigth,
            girl.Ninth, girl.Tenth, girl.Eleventh, girl.Twelveth,
            girl.Thirteenth, girl.Fourteenth, girl.Fifteenth, girl.Sixteenth
        )

        val total = boyAttributes.size
        var matches = 0

        val matchedTraits = mutableListOf<String>()
        val unmatchedTraits = mutableListOf<String>()

        for (i in boyAttributes.indices) {
            if (boyAttributes[i]?.equals(girlAttributes[i], ignoreCase = true) == true) {
                matches++
                matchedTraits.add(boyAttributes[i] ?: "")
            } else {
                unmatchedTraits.add(boyAttributes[i] ?: "")
            }
        }

        val matchPercentage = ((matches.toDouble() / total) * 100).toInt()
        return Triple(matchPercentage, matchedTraits, unmatchedTraits)
    }

    interface OnItremClickListener {
        fun onItemClick(malumot: Registration, matchPercentage: Int, matchedTraits: List<String>, unmatchedTraits: List<String>)
    }
}


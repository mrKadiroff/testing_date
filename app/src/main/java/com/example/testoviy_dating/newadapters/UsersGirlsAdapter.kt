package com.example.testoviy_dating.newadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.databinding.RegistrationLayoutBinding
import com.example.testoviy_dating.models.GirlsResponse
import com.example.testoviy_dating.newreg.BoysReg
import com.example.testoviy_dating.newreg.GirlsReg

class UsersGirlsAdapter(
    list: List<GirlsReg>,
    var boyReg: BoysReg,
    var onItremClickListener: OnItremClickListener
) : RecyclerView.Adapter<UsersGirlsAdapter.Vh>() {

    private var sortedList: List<GirlsRegWithPercentage> = calculateAndSortList(list, boyReg)

    inner class Vh(var itemUserBinding: RegistrationLayoutBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun onBind(girlRegWithPercentage: GirlsRegWithPercentage) {
            val malumot = girlRegWithPercentage.girlReg
            itemUserBinding.namecha.text = malumot.Name
            itemUserBinding.surnamecha.text = malumot.GirlsResponse?.First

            // Display the matching percentage
            itemUserBinding.gender.text = "${girlRegWithPercentage.matchingPercentage}%"

            itemUserBinding.root.setOnClickListener {
                onItremClickListener.onItemClick(malumot,girlRegWithPercentage.matchingPercentage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RegistrationLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(sortedList[position])
    }

    override fun getItemCount(): Int {
        return sortedList.size
    }

    interface OnItremClickListener {
        fun onItemClick(malumotlar: GirlsReg,percentage:Int)
    }

    private fun calculateMatchingPercentage(boyExpectations: GirlsResponse?, girlResponses: GirlsResponse?): Int {
        if (boyExpectations == null || girlResponses == null) return 0

        val boyExpectationList1to8 = listOf(
            boyExpectations.First, boyExpectations.Second, boyExpectations.Third, boyExpectations.Fourth,
            boyExpectations.Fifth, boyExpectations.Sixth, boyExpectations.Seventh, boyExpectations.Eigth
        )

        val girlResponseList1to8 = listOf(
            girlResponses.First, girlResponses.Second, girlResponses.Third, girlResponses.Fourth,
            girlResponses.Fifth, girlResponses.Sixth, girlResponses.Seventh, girlResponses.Eigth
        )

        val boyExpectationList9to16 = listOf(
            boyExpectations.Ninth, boyExpectations.Tenth, boyExpectations.Eleventh, boyExpectations.Twelveth,
            boyExpectations.Thirteenth, boyExpectations.Fourteenth, boyExpectations.Fifteenth, boyExpectations.Sixteenth
        )

        val girlResponseList9to16 = listOf(
            girlResponses.Ninth, girlResponses.Tenth, girlResponses.Eleventh, girlResponses.Twelveth,
            girlResponses.Thirteenth, girlResponses.Fourteenth, girlResponses.Fifteenth, girlResponses.Sixteenth
        )

        val matches1to8 = boyExpectationList1to8.zip(girlResponseList1to8).count { it.first == it.second }
        val matches9to16 = boyExpectationList9to16.count { boyExpectation ->
            girlResponseList9to16.any { girlResponse -> boyExpectation == girlResponse }
        }

        val totalMatches = matches1to8 + matches9to16
        val totalFields = boyExpectationList1to8.size + boyExpectationList9to16.size

        return (totalMatches * 100) / totalFields
    }

    private fun calculateAndSortList(list: List<GirlsReg>, boyReg: BoysReg): List<GirlsRegWithPercentage> {
        return list.map { girlReg ->
            val percentage = calculateMatchingPercentage(boyReg.BoysExpectation, girlReg.GirlsResponse)
            GirlsRegWithPercentage(girlReg, percentage)
        }.sortedByDescending { it.matchingPercentage }
    }

    fun updateList(newList: List<GirlsReg>) {
        sortedList = calculateAndSortList(newList, boyReg)
        notifyDataSetChanged()
    }

    data class GirlsRegWithPercentage(
        val girlReg: GirlsReg,
        val matchingPercentage: Int
    )
}

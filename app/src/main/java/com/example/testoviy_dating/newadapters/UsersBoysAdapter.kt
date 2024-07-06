package com.example.testoviy_dating.newadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.databinding.RegistrationLayoutBinding
import com.example.testoviy_dating.models.GirlsExpectation
import com.example.testoviy_dating.models.GirlsResponse
import com.example.testoviy_dating.newreg.BoysReg
import com.example.testoviy_dating.newreg.GirlsReg

class UsersBoysAdapter(
    var list: List<BoysReg>,
    var girlReg: GirlsReg,
    var onItremClickListener: OnItremClickListener
): RecyclerView.Adapter<UsersBoysAdapter.Vh>() {

    private var sortedList: List<UsersBoysAdapter.BoysRegWithPercentage> = calculateAndSortList(list, girlReg)

    inner class Vh(var itemUserBinding: RegistrationLayoutBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {


        fun onBind( boysRegWithPercentage: BoysRegWithPercentage) {
            var malumot = boysRegWithPercentage.boyReg
           itemUserBinding.namecha.text = malumot.Name
            itemUserBinding.surnamecha.text = malumot.BoysExpectation!!.First
// Display the matching percentage
            itemUserBinding.gender.text = "${boysRegWithPercentage.matchingPercentage}%"

            itemUserBinding.root.setOnClickListener {
                onItremClickListener.onItemClick(malumot)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RegistrationLayoutBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: UsersBoysAdapter.Vh, position: Int) {
        holder.onBind(sortedList[position])
    }

    override fun getItemCount(): Int {
        return sortedList.size
    }

    interface OnItremClickListener{
        fun onItemClick(malumotlar: BoysReg)
    }



    private fun calculateMatchingPercentage(girlExpectations: GirlsExpectation?, boyResponses: GirlsExpectation?): Int {
        if (girlExpectations == null || boyResponses == null) return 0

        val girlExpectationList1to9 = listOf(
            girlExpectations.First, girlExpectations.Second, girlExpectations.Third, girlExpectations.Fourth,
            girlExpectations.Fifth, girlExpectations.Sixth, girlExpectations.Seventh, girlExpectations.Eigth,girlExpectations.Ninth
        )

        val boyResponseList1to9 = listOf(
            boyResponses.First, boyResponses.Second, boyResponses.Third, boyResponses.Fourth,
            boyResponses.Fifth, boyResponses.Sixth, boyResponses.Seventh, boyResponses.Eigth,boyResponses.Ninth
        )

        val girlExpectationList10to14 = listOf(
            girlExpectations.Tenth, girlExpectations.Eleventh, girlExpectations.Twelveth,
            girlExpectations.Thirteenth, girlExpectations.Fourteenth
        )

        val boyResponseList10to14 = listOf(
            boyResponses.Tenth, boyResponses.Eleventh, boyResponses.Twelveth,
            boyResponses.Thirteenth, boyResponses.Fourteenth
        )

        val matches1to9 = girlExpectationList1to9.zip(boyResponseList1to9).count { it.first == it.second }
        val matches10to14 = girlExpectationList10to14.count { girExpectation ->
            boyResponseList10to14.any { boyResponse -> girExpectation == boyResponse }
        }

        val totalMatches = matches1to9 + matches10to14
        val totalFields = girlExpectationList1to9.size + girlExpectationList10to14.size

        return (totalMatches * 100) / totalFields
    }


    private fun calculateAndSortList(list: List<BoysReg>, girlReg: GirlsReg): List<UsersBoysAdapter.BoysRegWithPercentage> {
        return list.map { boyReg ->
            val percentage = calculateMatchingPercentage(girlReg.GirlsExpectation, boyReg.BoysResponse)
            UsersBoysAdapter.BoysRegWithPercentage(boyReg, percentage)
        }.sortedByDescending { it.matchingPercentage }
    }


    fun updateList(newList: List<BoysReg>) {
        sortedList = calculateAndSortList(newList, girlReg)
        notifyDataSetChanged()
    }





    data class BoysRegWithPercentage(
        val boyReg: BoysReg,
        val matchingPercentage: Int
    )







}
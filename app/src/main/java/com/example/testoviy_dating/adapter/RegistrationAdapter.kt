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
): RecyclerView.Adapter<RegistrationAdapter.Vh>() {

    inner class Vh(var itemUserBinding: RegistrationLayoutBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {


        @SuppressLint("SuspiciousIndentation")
        fun onBind(malumot: Registration) {


            itemUserBinding.namecha.text = malumot.Name
            itemUserBinding.surnamecha.text = malumot.Surname



                //boys expectaions
                firebaseFirestore.collection("boys_expectation")
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val result = it.result

                            result?.forEach { queryDocumentSnapshot ->
                                val boysExpecattions = queryDocumentSnapshot.toObject(GirlsResponse::class.java)


                                if (boysExpecattions.Password == password){
                                    //it here is the user's expectations information
                                    val matchPercentage = calculateMatchPercentage(boysExpecattions, girsResponse[adapterPosition])
                                    itemUserBinding.gender.text = "$matchPercentage% match"
                                }





                            }
                        }}








        }

        private fun calculateMatchPercentage(boy: GirlsResponse, girl: GirlsResponse): Int{
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

            for (i in boyAttributes.indices) {
                if (boyAttributes[i]?.equals(girlAttributes[i], ignoreCase = true) == true) {
                    matches++
                }
            }

            return ((matches.toDouble() / total) * 100).toInt()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RegistrationLayoutBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnItremClickListener{
        fun onItemClick(malumotlar: Registration)
    }


}
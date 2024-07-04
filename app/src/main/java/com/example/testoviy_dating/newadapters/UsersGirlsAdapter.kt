package com.example.testoviy_dating.newadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.databinding.RegistrationLayoutBinding
import com.example.testoviy_dating.newreg.BoysReg
import com.example.testoviy_dating.newreg.GirlsReg

class UsersGirlsAdapter(
    var list: List<GirlsReg>,
    var boyReg: BoysReg,
    var onItremClickListener: OnItremClickListener
): RecyclerView.Adapter<UsersGirlsAdapter.Vh>() {

    inner class Vh(var itemUserBinding: RegistrationLayoutBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {


        fun onBind(malumot: GirlsReg) {
           itemUserBinding.namecha.text = malumot.Name
            itemUserBinding.surnamecha.text = malumot.GirlsResponse?.First
            itemUserBinding.gender.text = "Percentage" //we need to write generated percentage here
           //one boy's (user's) expecation
//            boyReg.BoysExpectation!!.First
//            boyReg.BoysExpectation!!.Second.....




            //All girls responses to boys expectations
//            malumot.GirlsResponse!!.First
//            malumot.GirlsResponse!!.Second..

            itemUserBinding.root.setOnClickListener {
                onItremClickListener.onItemClick(malumot)
            }

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
        fun onItemClick(malumotlar: GirlsReg)
    }


}
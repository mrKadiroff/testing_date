package com.example.testoviy_dating.newadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.databinding.RegistrationLayoutBinding
import com.example.testoviy_dating.newreg.BoysReg

class UsersAdapter(var list: List<BoyReg>, var onItremClickListener: OnItremClickListener): RecyclerView.Adapter<UsersAdapter.Vh>() {

    inner class Vh(var itemUserBinding: RegistrationLayoutBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {


        fun onBind(malumot: BoyReg) {
           itemUserBinding.namecha.text = malumot.Name
            itemUserBinding.surnamecha.text = malumot.BoysQuest?.First

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
        fun onItemClick(malumotlar: BoyReg)
    }


}
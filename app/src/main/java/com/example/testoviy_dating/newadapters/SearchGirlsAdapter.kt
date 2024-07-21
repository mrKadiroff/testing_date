package com.example.testoviy_dating.newadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.databinding.RegistrationLayoutBinding
import com.example.testoviy_dating.newreg.BoysReg
import com.example.testoviy_dating.newreg.GirlsReg

class SearchGirlsAdapter(private var list: List<BoysReg>, private val onItremClickListener: OnItremClickListener) :
    RecyclerView.Adapter<SearchGirlsAdapter.Vh>() {

    inner class Vh(private val itemUserBinding: RegistrationLayoutBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {
        fun onBind(malumot: BoysReg) {
            itemUserBinding.namecha.text = malumot.Name
            itemUserBinding.surnamecha.text = malumot.BoysResponse!!.First
            itemUserBinding.root.setOnClickListener {
                onItremClickListener.onItemClick(malumot)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RegistrationLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<BoysReg>) {
        list = newList
        notifyDataSetChanged()
    }

    interface OnItremClickListener {
        fun onItemClick(malumotlar: BoysReg)
    }
}

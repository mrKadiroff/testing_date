package com.example.testoviy_dating.newadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.databinding.RegistrationLayoutBinding
import com.example.testoviy_dating.newreg.BoysReg
import com.example.testoviy_dating.newreg.GirlsReg

class SearchBoysAdapter(private var list: List<GirlsReg>, private val onItremClickListener: OnItremClickListener) :
    RecyclerView.Adapter<SearchBoysAdapter.Vh>() {

    inner class Vh(private val itemUserBinding: RegistrationLayoutBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {
        fun onBind(malumot: GirlsReg) {
            itemUserBinding.namecha.text = malumot.Name
            itemUserBinding.surnamecha.text = malumot.Region
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

    fun updateList(newList: List<GirlsReg>) {
        list = newList
        notifyDataSetChanged()
    }

    interface OnItremClickListener {
        fun onItemClick(malumotlar: GirlsReg)
    }
}

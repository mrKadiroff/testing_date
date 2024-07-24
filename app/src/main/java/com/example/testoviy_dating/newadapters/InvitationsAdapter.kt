package com.example.testoviy_dating.newadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.databinding.InvitaionLayoutBinding
import com.example.testoviy_dating.databinding.RegistrationLayoutBinding
import com.example.testoviy_dating.newreg.BoysReg
import com.example.testoviy_dating.newreg.Invitation

class InvitationsAdapter(
    var list: List<Invitation>,
    var onItemClickListener: OnItemClickListener,
    var onItemDeclineClickListener: OnItemDeclineClickListener,
    var onItemJustClickListener: OnItemJustClickListener
) : RecyclerView.Adapter<InvitationsAdapter.Vh>() {

    inner class Vh(var itemUserBinding: InvitaionLayoutBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun onBind(malumot: Invitation) {
            itemUserBinding.namecha.text = malumot.From
            itemUserBinding.surnamecha.text = malumot.To

            itemUserBinding.accept.setOnClickListener {
                onItemClickListener.onItemClick(malumot)
            }
            itemUserBinding.decline.setOnClickListener {
                onItemDeclineClickListener.onItemDeclineClick(malumot)
            }

            itemUserBinding.root.setOnClickListener {
                onItemJustClickListener.onItemJustClick(malumot)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(InvitaionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(malumot: Invitation)
    }

    interface OnItemDeclineClickListener {
        fun onItemDeclineClick(malumot: Invitation)
    }



    interface OnItemJustClickListener {
        fun onItemJustClick(malumot: Invitation)
    }
}

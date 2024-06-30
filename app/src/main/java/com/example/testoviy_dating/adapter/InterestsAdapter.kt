package com.example.testoviy_dating.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.R
import com.example.testoviy_dating.models.BoysTraits

class InterestsAdapter (private val traits: List<BoysTraits>) : RecyclerView.Adapter<InterestsAdapter.TraitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trait, parent, false)
        return TraitViewHolder(view)
    }

    override fun onBindViewHolder(holder: TraitViewHolder, position: Int) {
        holder.bind(traits[position])
    }

    override fun getItemCount(): Int = traits.size

    inner class TraitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBoxTrait: CheckBox = itemView.findViewById(R.id.checkBoxTrait)

        fun bind(trait: BoysTraits) {
            checkBoxTrait.text = trait.name
            checkBoxTrait.isChecked = trait.isSelected

            checkBoxTrait.setOnCheckedChangeListener { _, isChecked ->
                trait.isSelected = isChecked
            }
        }
    }

    fun getSelectedTraits(): List<BoysTraits> {
        return traits.filter { it.isSelected }
    }
}
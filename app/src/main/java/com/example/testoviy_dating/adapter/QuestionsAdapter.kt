package com.example.testoviy_dating.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testoviy_dating.R
import com.example.testoviy_dating.models.Question

class QuestionsAdapter(private val questions: List<Question>) :
    RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int = questions.size

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvQuestion: TextView = itemView.findViewById(R.id.tvQuestion)
        private val rgChoices: RadioGroup = itemView.findViewById(R.id.rgChoices)

        fun bind(question: Question) {
            tvQuestion.text = question.questionText
            rgChoices.removeAllViews()

            question.choices.forEachIndexed { index, choice ->
                val radioButton = RadioButton(itemView.context)
                radioButton.text = choice
                radioButton.id = View.generateViewId() // Generate a unique ID for each RadioButton

                rgChoices.addView(radioButton)
            }

            // Temporarily remove listener to avoid triggering it when setting the checked state
            rgChoices.setOnCheckedChangeListener(null)

            // Set the checked state based on the selectedChoiceIndex
            if (question.selectedChoiceIndex >= 0 && question.selectedChoiceIndex < rgChoices.childCount) {
                (rgChoices.getChildAt(question.selectedChoiceIndex) as RadioButton).isChecked = true
            } else {
                rgChoices.clearCheck() // Clear the check if selectedChoiceIndex is invalid
            }

            // Re-attach the listener
            rgChoices.setOnCheckedChangeListener { group, checkedId ->
                val selectedIndex = rgChoices.indexOfChild(group.findViewById(checkedId))
                question.selectedChoiceIndex = selectedIndex
            }
        }
    }

    fun getResults(): List<String> {
        return questions.map { it.getSelectedChoiceLetter() }
    }
}
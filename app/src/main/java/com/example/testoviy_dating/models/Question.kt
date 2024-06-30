package com.example.testoviy_dating.models

data class Question(
    val questionText: String,
    val choices: List<String>,
    var selectedChoiceIndex: Int = -1
){
    fun getSelectedChoiceLetter(): String {
        return when (selectedChoiceIndex) {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            else -> "N/A" // If no selection, return "N/A"
        }
    }
}

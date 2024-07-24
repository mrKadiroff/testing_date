package com.example.testoviy_dating.newreg

data class Invitation(
    var From: String? = null,
    var To: String? = null,
    var Status: String? = null,
    var Timestamp: String? = null,
    var documentId: String? = null // Add this field
)
package com.example.testoviy_dating.newadapters

import java.io.Serializable

data class BoyReg(
    var Age: String? = null,
    var BoysQuest: GirlsResponse? = null,
    var BoysResponse: GirlsExpectation? = null,
    var Gender: String? = null,
    var Name: String? = null,
    var Password: String? = null,
    var RecoveryAccount: String? = null,
    var Surname: String? = null
) : Serializable

data class GirlsResponse(
    var First: String? = null,
    var Second: String? = null,
    var Third: String? = null,
    var Fourth: String? = null,
    var Fifth: String? = null,
    var Sixth: String? = null,
    var Seventh: String? = null,
    var Eigth: String? = null,
    var Ninth: String? = null,
    var Tenth: String? = null,
    var Eleventh : String? = null,
    var Twelveth: String? = null,
    var Thirteenth: String? = null,
    var Fourteenth: String? = null,
    var Fifteenth: String? = null,
    var Sixteenth: String? = null
) : java.io.Serializable

data class GirlsExpectation(
    var First: String? = null,
    var Second: String? = null,
    var Third: String? = null,
    var Fourth: String? = null,
    var Fifth: String? = null,
    var Sixth: String? = null,
    var Seventh: String? = null,
    var Eigth: String? = null,
    var Ninth: String? = null,
    var Tenth: String? = null,
    var Eleventh: String? = null,
    var Twelveth: String? = null,
    var Thirteenth: String? = null,
    var Fourteenth: String? = null
) : java.io.Serializable

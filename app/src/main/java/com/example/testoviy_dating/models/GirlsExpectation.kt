package com.example.testoviy_dating.models

import java.io.Serializable

class GirlsExpectation :Serializable{

    var First: String? = null
    var Second: String? = null
    var Third: String? = null
    var Fourth: String? = null
    var Fifth: String? = null
    var Sixth: String? = null
    var Seventh: String? = null
    var Eigth: String? = null
    var Ninth: String? = null
    var Tenth: String? = null
    var Eleventh: String? = null
    var Twelveth: String? = null
    var Thirteenth: String? = null
    var Fourteenth: String? = null


    constructor(
        First: String?,
        Second: String?,
        Third: String?,
        Fourth: String?,
        Fifth: String?,
        Sixth: String?,
        Seventh: String?,
        Eigth: String?,
        Ninth: String?,
        Tenth: String?,
        Eleventh: String?,
        Twelveth: String?,
        Thirteenth: String?,
        Fourteenth: String?
    ) {
        this.First = First
        this.Second = Second
        this.Third = Third
        this.Fourth = Fourth
        this.Fifth = Fifth
        this.Sixth = Sixth
        this.Seventh = Seventh
        this.Eigth = Eigth
        this.Ninth = Ninth
        this.Tenth = Tenth
        this.Eleventh = Eleventh
        this.Twelveth = Twelveth
        this.Thirteenth = Thirteenth
        this.Fourteenth = Fourteenth
    }

    constructor()


}
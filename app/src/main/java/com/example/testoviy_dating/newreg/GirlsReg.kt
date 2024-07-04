package com.example.testoviy_dating.newreg

import com.example.testoviy_dating.models.GirlsExpectation
import com.example.testoviy_dating.models.GirlsResponse
import java.io.Serializable

class GirlsReg : Serializable{
    var Name: String? = null
    var Surname: String? = null
    var Age: String? = null
    var Gender:String? = null
    var Password: String? = null
    var RecoveryAccount: String? = null
    var GirlsExpectation: GirlsExpectation? = null
    var GirlsResponse: GirlsResponse? = null








    constructor()
    constructor(
        Name: String?,
        Surname: String?,
        Age: String?,
        Gender: String?,
        Password: String?,
        RecoveryAccount: String?,
        GirlsExpectation: GirlsExpectation?,
        GirlsResponse: GirlsResponse?
    ) {
        this.Name = Name
        this.Surname = Surname
        this.Age = Age
        this.Gender = Gender
        this.Password = Password
        this.RecoveryAccount = RecoveryAccount
        this.GirlsExpectation = GirlsExpectation
        this.GirlsResponse = GirlsResponse
    }


}
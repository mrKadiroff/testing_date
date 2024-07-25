package com.example.testoviy_dating.newreg

import com.example.testoviy_dating.models.GirlsExpectation
import com.example.testoviy_dating.models.GirlsResponse
import java.io.Serializable

class BoysReg : Serializable{
    var Name: String? = null
    var Surname: String? = null
    var Age: String? = null
    var Gender:String? = null
    var Password: String? = null
    var RecoveryAccount: String? = null
    var Region: String? = null
    var Token: String? = null
    var BoysExpectation: GirlsResponse? = null
    var BoysResponse: GirlsExpectation? = null







    constructor()
    constructor(
        Name: String?,
        Surname: String?,
        Age: String?,
        Gender: String?,
        Password: String?,
        RecoveryAccount: String?,
        Region: String?,
        Token: String?,
        BoysExpectation: GirlsResponse?,
        BoysResponse: GirlsExpectation?
    ) {
        this.Name = Name
        this.Surname = Surname
        this.Age = Age
        this.Gender = Gender
        this.Password = Password
        this.RecoveryAccount = RecoveryAccount
        this.Region = Region
        this.Token = Token
        this.BoysExpectation = BoysExpectation
        this.BoysResponse = BoysResponse
    }


}
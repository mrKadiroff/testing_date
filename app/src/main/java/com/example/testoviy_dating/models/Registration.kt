package com.example.testoviy_dating.models

import java.io.Serializable

class Registration : Serializable{
    var Name: String? = null
    var Surname: String? = null
    var Age: String? = null
    var Gender:String? = null
    var Password: String? = null
    var RecoveryAccount: String? = null




    constructor(
        Name: String?,
        Surname: String?,
        Age: String?,
        Gender: String?,
        Password: String?,
        RecoveryAccount: String?
    ) {
        this.Name = Name
        this.Surname = Surname
        this.Age = Age
        this.Gender = Gender
        this.Password = Password
        this.RecoveryAccount = RecoveryAccount
    }

    constructor()
}
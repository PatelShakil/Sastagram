package com.genie.account

class UserModel {
    lateinit var username: String
    lateinit var email:String
    lateinit var password:String
    lateinit var name:String
    constructor()
    constructor(username: String, email: String, password: String, name: String) {
        this.username = username
        this.email = email
        this.password = password
        this.name = name
    }
}
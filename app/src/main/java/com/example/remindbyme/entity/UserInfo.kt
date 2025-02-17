package com.example.remindbyme.entity

data class UserInfo(
    val id: Int,
    val name: String,
    val mobileNumber: String,
    val profileImage: String,
    val mail: String,
    val addressOne: String,
    val addressTwo: String,
    val state: String,
    val country: String,
    val zipCode: String
){
    override fun toString(): String {
        return "UserInfo(id=$id, name='$name', mobileNumber='$mobileNumber', profileImage='$profileImage', mail='$mail', addressOne='$addressOne', addressTwo='$addressTwo', state='$state', country='$country', zipCode='$zipCode')"
    }
}
package com.example.recyclerviewwithdatastore

import java.io.Serializable

data class User (
    val userId: Int?,
    val id: Int?,
    val title: String?,
    val body: String?,
    var isSaved: Boolean,
    ):Serializable
//
//data class Users (
//    val users : List<User?>?
//)

package com.example.recyclerviewwithdatastore

import retrofit2.Call
import retrofit2.http.GET

//https://jsonplaceholder.typicode.com/posts

interface UserInterface {
    @GET("posts")
    fun getUserData() : Call<List<User>>
}

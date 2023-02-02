package com.example.recyclerviewwithdatastore

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferenceManager(context: Context) {

    companion object {
        const val USER_DETAILS = "key.user.details"
    }
    private val mySharedPreferences: SharedPreferences =
        context.getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor? = mySharedPreferences.edit()

    fun getUserList() : MutableList<User>? {
        var savedList:List<User>?= arrayListOf()
        val user = mySharedPreferences.getString(USER_DETAILS, null)
        if(user?.isNotEmpty()==true) {
            val type = object: TypeToken<ArrayList<User>>(){}.type
            savedList = Gson().fromJson<ArrayList<User>>(user,type)

        }
        return savedList?.toMutableList()
    }

    fun saveUserInfo(userData: User) {
        val getUserList = getUserList()
        val userModel = getUserList?.find { it.id == userData.id }
        if (userModel==null){
            userData.let { getUserList?.add(it) }
            val user = Gson().toJson(getUserList)
            editor?.putString(USER_DETAILS, user)?.apply()
        }
    }

    fun removeUserFromList(userData: User?) {
        val getUserList = getUserList()
        val userModel = getUserList?.find { it.id == userData?.id }
        if (userModel!=null){
            userData?.let { getUserList.remove(userModel) }
            val user = Gson().toJson(getUserList)
            editor?.putString(USER_DETAILS, user)?.apply()
//            Log.d("get", "update: " + getUserList?.size)

        }


    }
}
package com.example.demo

import android.content.Context
import android.content.SharedPreferences

class Session(var ctx: Context) {
    var prefs: SharedPreferences = ctx.getSharedPreferences("todo-app", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = prefs.edit()

    fun setLoggedin(logggedin: Boolean,emial:String,pass:String,name:String,id:String) {
        editor.putBoolean("loggedInmode", logggedin)
        editor.putString("email", emial)
        editor.putString("pass", pass)
        editor.putString("name",name)
        editor.putString("id",id)
        editor.commit()
    }
    fun loggedin(): Boolean {
        return prefs.getBoolean("loggedInmode", false)
    }

    fun getName(): String? {
        return prefs.getString("name","empty")
    }
    fun getId(): String? {
        return prefs.getString("id","empty")
    }
    fun getPassword(): String? {
        return prefs.getString("pass","empty")
    }
    fun getEmail(): String? {
        return prefs.getString("email","empty")
    }
    fun removeAll() {
        prefs.edit().remove("todo-app").commit()
    }
}
package com.abdulqohar.mapdraweractivitypractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.json.JSONArray

class JsonActivity : AppCompatActivity() {
    private lateinit var usersList: ArrayList<UserX>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)

        bindJsonDataInFacilityList()
        Log.d("user", usersList.toString())
    }

    private fun jsonDataFromAssets(): String? {
        var json:String? = null
        return applicationContext.assets.open("user.json").bufferedReader().use {
            it.readText()
        }
    }

    private fun bindJsonDataInFacilityList(){
        usersList = arrayListOf()
        val userJsonArray = JSONArray(jsonDataFromAssets())
        for (i in 0 until userJsonArray.length() - 1){
            val user = UserX()
            val currentUserObject = userJsonArray.getJSONObject(i)
            user.name = currentUserObject.getString("name")
            user.id = currentUserObject.getString("id")
            user.age = currentUserObject.getString("age")
            usersList.add(user)
        }
        Toast.makeText(this, "$usersList", Toast.LENGTH_SHORT).show()
    }
}
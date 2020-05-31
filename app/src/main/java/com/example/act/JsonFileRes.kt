package com.example.act

import android.content.Context
import com.google.gson.Gson
import java.util.ArrayList

open class JsonFileRes {
    data class Jsonact (
        val actname : String,
        val actimageurl : String,
        val actdescribe : String,
        val product : ArrayList<Jproduct>
    )
    data class Jproduct (
        val imagename : String?,
        val imageurl : String,
        val describe : String
    ) // json array 包覆 object
    data class Jtype (
        val act : ArrayList<Jsonact>
    )
    fun readJsonFile(context: Context): Jtype {
        val jsonData : Jtype
        val jsonFile = context.applicationContext.assets.open("project.json").bufferedReader().use {
            it.readText()
        } // 解析json file
        jsonData = Gson().fromJson(jsonFile, Jtype::class.java)
        return jsonData
    }
}

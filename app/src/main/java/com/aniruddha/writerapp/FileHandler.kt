package com.aniruddha.writerapp

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.*
import java.lang.StringBuilder

class FileHandler{
    private lateinit var data : String
    companion object
    {
        private var instance : FileHandler? = null

        fun getInstance():FileHandler{
            if(instance == null) {
                instance = FileHandler()
                return instance as FileHandler
            }
            return instance as FileHandler
        }
    }

    fun getStringData(file : File):String{
        var text : String? = null
        var bufferedReader = BufferedReader(InputStreamReader(FileInputStream(file)))
        while( { text = bufferedReader.readLine(); text}() != null){
            StringBuilder(data).append(text).append(".")
        }
        return data
    }
}
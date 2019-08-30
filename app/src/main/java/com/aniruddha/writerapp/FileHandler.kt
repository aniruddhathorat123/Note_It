package com.aniruddha.writerapp

import java.io.File
import java.io.FileInputStream
import java.io.*
import java.lang.StringBuilder

class FileHandler{
    private lateinit var data : ArrayList<String>
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

    fun getStringData(file : File):ArrayList<String>{
        var text : String? = null
        val bufferedReader = BufferedReader(InputStreamReader(FileInputStream(file)))
        while( { text = bufferedReader.readLine(); text}() != null){
            data.add(text.toString())
        }
        return data
    }

    fun putStringData(data: String){

    }
}
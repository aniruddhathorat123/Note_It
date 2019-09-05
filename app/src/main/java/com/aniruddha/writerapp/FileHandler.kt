package com.aniruddha.writerapp

import java.io.File

/**
 * File operations are more easy in Kotlin and we are going to use it.
 * File().readText() or writeText() can able to handle data up to size 2GB.
 */
class FileHandler{
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

    private lateinit var data : ArrayList<String>
    private val location = WriterConstants.FILE_STORAGE_LOCATION

    fun getFileData(file : String):String {
        return File("$location/$file").readText()
    }

    fun saveFileData(file: String, data: String) {
        File("$location/$file").writeText(data)
    }

    fun createNewFile(file: String) {
        println("Create file path:$location")
        File("$location/$file").createNewFile()
    }

    fun deleteFile(file: String) {
        File("$location/$file").delete()
    }

    fun renameFile(oldName: String, newName: String) {
        File("$location/$oldName").renameTo(File("$location/$newName"))
    }
}
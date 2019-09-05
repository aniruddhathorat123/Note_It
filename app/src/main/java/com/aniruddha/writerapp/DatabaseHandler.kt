package com.aniruddha.writerapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,
    WriterConstants.DATABASE_NAME,null,
    WriterConstants.DATABASE_VERSION) {
    //private val db = this.writableDatabase
    companion object{
        private var instance : DatabaseHandler? = null
        fun getInstance(context:Context): DatabaseHandler {
            if (instance != null)
                return instance as DatabaseHandler
            instance = DatabaseHandler(context)
            return instance as DatabaseHandler
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(WriterConstants.NEW_USER_DATABASE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun getFileList():MutableList<String> {
        val db = this.writableDatabase
        val list  = mutableListOf<String>()
        val cursor : Cursor = db.rawQuery(WriterConstants.GET_FILES_NAME_QUERY,null)
        if (cursor.count>0 && cursor.moveToFirst()){
            do{
                list.add(cursor.getString(cursor.getColumnIndex("Files")))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun insertFileList(name: String){
        WriterConstants.NEW_FILE_NAME = name
        //this.writableDatabase.execSQL(WriterConstants.INSERT_USER_FILE_QUERY,Array<String>(1){name})
        this.writableDatabase.insert(WriterConstants.TABLE_NAME,
            null,
            ContentValues().apply { put("Files",name) })
        WriterConstants.NEW_FILE_NAME = ""
    }

    fun updateFileName(oldName: String,newName: String){
        /*lateinit var recName: String
        for(i in 0..oldName.length)
            if(oldName[i]==' ')
                recName*/
        this.writableDatabase.update(WriterConstants.TABLE_NAME,
            ContentValues().apply { put("Files",newName) },
            "Files=?",Array(1){oldName})
    }

    fun deleteFile(name: String){
        this.writableDatabase.delete(WriterConstants.TABLE_NAME,
            "Files=?",Array(1){name})
    }
}
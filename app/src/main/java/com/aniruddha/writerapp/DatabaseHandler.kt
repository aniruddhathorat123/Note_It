package com.aniruddha.writerapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,
    WriterConstants.DATABASE_NAME,null,
    WriterConstants.DATABASE_VERSION
){

    companion object{
        private var instance : DatabaseHandler? = null
        fun getIntstence(context:Context): DatabaseHandler {
            if (instance != null)
                return instance as DatabaseHandler
            instance = DatabaseHandler(context)
            return instance as DatabaseHandler
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun getFileList():MutableList<String>{
        val db = this.writableDatabase
        lateinit var list : MutableList<String>
        val cursor : Cursor = db.rawQuery(WriterConstants.GET_FILES_NAME_QUERY,null)

        if (cursor.moveToFirst()){
            do{
                list.add(cursor.getString(cursor.getColumnIndex("files")))
            }while (cursor.moveToNext())
        }
        return list
    }

    fun setFileList(){
        val db = this.writableDatabase
        db.execSQL(WriterConstants.NEW_USER_FILES_QUERY)
    }

}
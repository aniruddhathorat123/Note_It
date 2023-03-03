package com.aniruddha.writerapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,
    WriterConstants.DATABASE_NAME,null,
    WriterConstants.DATABASE_VERSION) {
    private val db = this.writableDatabase
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
        // Create new column : `creationDate` which stores the date for each created file.
        if (newVersion == 2 && db != null) {
            db.execSQL(WriterConstants.ALTER_TABLE_ADD_CREATION_DATE_QUERY)
        }
    }

    fun getFileList():MutableList<MutableList<String>> {
        val result = mutableListOf<MutableList<String>>()
        val cursor : Cursor = db.rawQuery(WriterConstants.GET_FILES_NAME_QUERY,null)
        if (cursor.count > 0 && cursor.moveToFirst()){
            do{
                val nameColumnIndex = cursor.getColumnIndex(WriterConstants.COLUMN_NAME_FILES)
                val DOCColumnIndex = cursor.getColumnIndex(WriterConstants.COLUMN_NAME_DATE_OF_CREATION)
                // columnIndex can be -1.
                if (nameColumnIndex != -1 && DOCColumnIndex != -1) {
                    val data = mutableListOf<String>()
                    data.add(cursor.getString(nameColumnIndex))
                    data.add(cursor.getString(DOCColumnIndex))
                    result.add(data)
                }
            }while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    fun insertFileList(name: String, date: String){
        WriterConstants.NEW_FILE_NAME = name
        db.insert(WriterConstants.TABLE_NAME,
            null,
            ContentValues().apply {
                put(WriterConstants.COLUMN_NAME_FILES,name)
                put(WriterConstants.COLUMN_NAME_DATE_OF_CREATION, date)
            })
        WriterConstants.NEW_FILE_NAME = ""
    }

    fun updateFileName(oldName: String,newName: String){
        db.update(WriterConstants.TABLE_NAME,
            ContentValues().apply { put("Files",newName) },
            "Files=?",Array(1){oldName})
    }

    fun deleteFile(name: String){
        db.delete(WriterConstants.TABLE_NAME,
            "Files=?",Array(1){name})
    }
}
package com.aniruddha.writerapp

import android.app.Application

class SqliteManager() : Application(){
    var instance = DatabaseHandler.getInstance(applicationContext)

}
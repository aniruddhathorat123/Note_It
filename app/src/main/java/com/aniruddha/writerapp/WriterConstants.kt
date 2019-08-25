package com.aniruddha.writerapp

class WriterConstants{
    companion object{
        val WRITER_FILE = "writer_user_data.txt"
        val DATABASE_NAME = "WRITER_DB1"
        val DATABASE_VERSION = 1
        var USER_ID = "0"
        val TABLE_NAME = "writer_table_list"
        val GET_FILES_NAME_QUERY = "SELECT * FROM $TABLE_NAME"
        val NEW_USER_FILES_QUERY = "CREATE TABLE $TABLE_NAME (Files TEXT)"
        val FILES_LIST = "files_list"
    }

}
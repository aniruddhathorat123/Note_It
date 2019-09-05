package com.aniruddha.writerapp

class WriterConstants{
    companion object{
        val STORAGE_PERMISSION_CODE = 1
        val WRITER_FILE = "writer_user_data.txt"
        val DATABASE_NAME = "WRITER_DB1"
        val DATABASE_VERSION = 1
        var USER_ID = "DEMO"
        var NEW_FILE_NAME = ""
        val TABLE_NAME = "writer_table_list"
        val GET_FILES_NAME_QUERY = "SELECT * FROM $TABLE_NAME"
        val NEW_USER_DATABASE_QUERY = "CREATE TABLE $TABLE_NAME (Files TEXT)"
        val INSERT_USER_FILE_QUERY = "INSERT INTO $TABLE_NAME VALUES "
        val FILES_LIST = "files_list"
        var FILE_STORAGE_LOCATION = "storage_location"
        var FILE_NAME = "file_name"
    }

}
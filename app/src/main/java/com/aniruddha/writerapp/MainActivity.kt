package com.aniruddha.writerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var filehandler: FileHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var file = File(WriterConstants.WRITER_FILE)
        if (file.exists()){
            filehandler = FileHandler.getInstance()
            var data = filehandler.getStringData(file)

        }
        else{

        }
    }
}

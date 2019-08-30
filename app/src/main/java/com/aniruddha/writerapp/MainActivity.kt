package com.aniruddha.writerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = FilesListFragment.getInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fileListFragment,fragment)
            .commit()
    }
}

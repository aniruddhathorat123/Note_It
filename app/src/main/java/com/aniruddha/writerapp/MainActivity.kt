package com.aniruddha.writerapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: FilesListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState==null) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
                startFragment()
            } else {
                showPermissionDialog()
            }
        }
    }

    private fun showPermissionDialog(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            AlertDialog.Builder(this)
                .setTitle(R.string.permissionTitle)
                .setMessage(R.string.permissionList)
                .setPositiveButton("Ok"){dialog, which ->
                    ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE ,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        WriterConstants.STORAGE_PERMISSION_CODE)
                }
                .setNegativeButton("Not Now"){dialog, which ->
                }
                .create()
                .show()
        }else{
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WriterConstants.STORAGE_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        when(requestCode) {
            WriterConstants.STORAGE_PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() &&
                    grantResults[0]==PackageManager.PERMISSION_GRANTED  &&
                    grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                    startFragment()
                }
                else{
                    exitProcess(-1)
                }
            }
        }
    }

    private fun startFragment() {
        WriterConstants.FILE_STORAGE_LOCATION = applicationContext.filesDir.toString()
        fragment = FilesListFragment.getInstance()
        replaceFragment(fragment)
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fileListFragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * This method is used when user pressed the back button while editing the file.
     * Mostly same as clickListener in Recycler View.
     */
    override fun onBackPressed() {
        callFragment()
        super.onBackPressed()
    }

    private fun callFragment(){
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            when (f) {
                is FileDataContainerFragment -> {
                    f.saveEditData()
                }
                is FilesListFragment -> {
                    finish()
                }
            }
        }
    }
}
